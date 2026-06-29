package com.pinangflow.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pinangflow.app.domain.model.ExportTransaction
import com.pinangflow.app.domain.repository.ExportRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExportRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val whatsappHelper: WhatsAppHelper
) : ExportRepository {

    override fun getExportTransactions(): Flow<List<ExportTransaction>> = callbackFlow {
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            close(Exception("Belum login"))
            return@callbackFlow
        }
        val listener = firestore.collection("users").document(uid).collection("penjualan_ekspor")
            .orderBy("tanggalKeluar", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        ExportTransaction(
                            id = doc.id,
                            tanggalKeluar = doc.getString("tanggalKeluar") ?: "",
                            namaEksportir = doc.getString("namaEksportir") ?: "",
                            beratJualKg = doc.getDouble("beratJualKg") ?: 0.0,
                            hargaJualPerKg = doc.getLong("hargaJualPerKg") ?: 0L,
                            totalPendapatan = doc.getLong("totalPendapatan") ?: 0L,
                            catatan = doc.getString("catatan") ?: ""
                        )
                    }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addExportTransaction(
        transactionModel: ExportTransaction,
        ownerWaNumber: String
    ): Result<String> {
        return try {
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("Belum login")

            // 1. Ambil Profil User untuk mengecek Token WA
            val profileDoc = firestore.collection("users").document(uid).get().await()
            val tokenWaGateway = profileDoc.getString("tokenWaGateway") ?: ""
            val nomorWaProfil = profileDoc.getString("nomorWaLaporan") ?: ""
            
            var sisaStokKering = 0.0
            
            firestore.runTransaction { transaction ->
                val statsRef = firestore.collection("users").document(uid).collection("stats").document("global")
                val statsSnap = transaction.get(statsRef)

                val currentKeringTons = (statsSnap.get("totalStokKering") as? Number)?.toDouble() ?: 0.0
                val beratJualTons = transactionModel.beratJualKg / 1000.0

                if (currentKeringTons < beratJualTons) {
                    throw Exception("Stok pinang kering tidak mencukupi! Sisa: ${currentKeringTons * 1000} Kg")
                }

                // Pengurangan stok
                val newKeringTons = currentKeringTons - beratJualTons
                sisaStokKering = newKeringTons * 1000 // dalam Kg untuk keperluan pesan WA
                
                // Tambah stok terjual
                val currentStokTerjual = (statsSnap.get("stokTerjual") as? Number)?.toLong() ?: 0L
                val newStokTerjual = currentStokTerjual + transactionModel.beratJualKg.toLong()

                val updates = mapOf(
                    "totalStokKering" to newKeringTons,
                    "stokTerjual" to newStokTerjual
                )
                
                if (statsSnap.exists()) {
                    transaction.update(statsRef, updates)
                }

                val exportRef = firestore.collection("users").document(uid).collection("penjualan_ekspor").document(transactionModel.id)
                val exportData = mapOf(
                    "id" to transactionModel.id,
                    "tanggalKeluar" to transactionModel.tanggalKeluar,
                    "namaEksportir" to transactionModel.namaEksportir,
                    "beratJualKg" to transactionModel.beratJualKg,
                    "hargaJualPerKg" to transactionModel.hargaJualPerKg,
                    "totalPendapatan" to transactionModel.totalPendapatan,
                    "catatan" to transactionModel.catatan
                )
                transaction.set(exportRef, exportData)
            }.await()
            
            // Kirim WA menggunakan token dari profil pengepul
            val targetNumber = if (ownerWaNumber.isNotBlank()) ownerWaNumber else nomorWaProfil
            
            if (targetNumber.isNotBlank()) {
                val hargaStr = String.format("%,d", transactionModel.hargaJualPerKg).replace(',', '.')
                val totalStr = String.format("%,d", transactionModel.totalPendapatan).replace(',', '.')
                val beratStr = if (transactionModel.beratJualKg % 1.0 == 0.0) {
                    String.format("%,d", transactionModel.beratJualKg.toLong()).replace(',', '.')
                } else {
                    transactionModel.beratJualKg.toString()
                }
                
                val sisaStr = if (sisaStokKering % 1.0 == 0.0) {
                    String.format("%,d", sisaStokKering.toLong()).replace(',', '.')
                } else {
                    String.format("%.1f", sisaStokKering).replace(',', '.')
                }

                val pesan = """
                    *[ NOTIFIKASI PINANG KELUAR / EKSPOR ]*
                    Stok berhasil dikeluarkan untuk Eksportir: *${transactionModel.namaEksportir}*
                    Total Berat: *$beratStr Kg*
                    Harga Jual: *Rp $hargaStr/Kg*
                    Total Pendapatan Masuk: *Rp $totalStr*
                    Sisa Stok Kering di Gudang saat ini: *$sisaStr Kg*
                """.trimIndent()
                
                try {
                    whatsappHelper.sendNotification(targetNumber, pesan, tokenWaGateway)
                } catch (e: Exception) {
                    android.util.Log.e("ExportRepository", "Gagal mengirim pesan WA: ${e.message}")
                }
            }

            if (tokenWaGateway.isBlank()) {
                Result.success("Penjualan tersimpan! (Saran: Untuk kirim notifikasi via WA, silakan masukkan Token Fonnte di menu Profil terlebih dahulu)")
            } else {
                Result.success("Penjualan ekspor berhasil disimpan & WA terkirim!")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
