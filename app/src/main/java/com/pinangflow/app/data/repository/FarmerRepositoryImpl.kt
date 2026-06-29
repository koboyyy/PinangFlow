package com.pinangflow.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pinangflow.app.domain.model.Farmer
import com.pinangflow.app.domain.model.FarmerTransaction
import com.pinangflow.app.domain.model.TransactionType
import com.pinangflow.app.domain.repository.FarmerRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

import com.google.firebase.auth.FirebaseAuth

class FarmerRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val whatsappHelper: WhatsAppHelper
) : FarmerRepository {

    override fun getFarmers(): Flow<List<Farmer>> = callbackFlow {
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            close(Exception("Belum login"))
            return@callbackFlow
        }
        val listener = firestore.collection("users").document(uid).collection("farmers")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        mapDocToFarmer(doc.id, doc.data)
                    }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }

    override fun getFarmer(farmerId: String): Flow<Farmer?> = callbackFlow {
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            close(Exception("Belum login"))
            return@callbackFlow
        }
        val listener = firestore.collection("users").document(uid).collection("farmers").document(farmerId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    trySend(if (snapshot.exists()) mapDocToFarmer(snapshot.id, snapshot.data) else null)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addFarmer(farmer: Farmer): Result<Unit> {
        return try {
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("Belum login")
            val data = mapOf(
                "nama" to farmer.nama,
                "nomorTelepon" to farmer.nomorTelepon,
                "alamat" to farmer.alamat,
                "luasLahan" to farmer.luasLahan,
                "jenisTanaman" to farmer.jenisTanaman,
                "statusAktif" to farmer.statusAktif,
                "fotoUrl" to farmer.fotoUrl,
                "totalDisetor" to farmer.totalDisetor,
                "sisaKasbon" to farmer.sisaKasbon
            )
            firestore.collection("users").document(uid).collection("farmers").document(farmer.id).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getFarmerTransactions(farmerId: String): Flow<List<FarmerTransaction>> = callbackFlow {
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            close(Exception("Belum login"))
            return@callbackFlow
        }
        val listener = firestore.collection("users").document(uid).collection("farmers").document(farmerId)
            .collection("transactions")
            .orderBy("tanggal", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        mapDocToTransaction(doc.id, doc.data)
                    }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addTransaction(farmerId: String, transaction: FarmerTransaction): Result<Unit> {
        return try {
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("Belum login")
            firestore.runTransaction { firestoreTransaction ->
                // === 1. READ ALL DOCUMENTS FIRST ===
                val farmerRef = firestore.collection("users").document(uid).collection("farmers").document(farmerId)
                val farmerSnap = firestoreTransaction.get(farmerRef)
                if (!farmerSnap.exists()) {
                    throw Exception("Petani tidak ditemukan!")
                }

                val statsRef = firestore.collection("users").document(uid).collection("stats").document("global")
                val statsSnap = firestoreTransaction.get(statsRef)

                // === 2. CALCULATIONS ===
                val farmerName = farmerSnap.getString("nama") ?: "Petani"
                val currentDisetor = (farmerSnap.get("totalDisetor") as? Number)?.toDouble() ?: 0.0
                val currentKasbon = (farmerSnap.get("sisaKasbon") as? Number)?.toDouble() ?: 0.0

                val sisaKasbonChange = transaction.jumlah.toDouble()
                val newKasbon = (currentKasbon + sisaKasbonChange).coerceAtLeast(0.0)

                val weight = if (transaction.jenis == TransactionType.SETOR) {
                    val regex = Regex("""Setor\s+([\d.,]+)\s*Kg""")
                    val match = regex.find(transaction.deskripsi)
                    match?.groupValues?.get(1)?.replace(".", "")?.replace(",", ".")?.toDoubleOrNull() ?: 0.0
                } else 0.0

                val newDisetor = currentDisetor + weight

                val currentAktifKasbon = (statsSnap.get("aktifKasbon") as? Number)?.toLong() ?: 0L
                val currentUangKeluar = (statsSnap.get("uangKeluar") as? Number)?.toLong() ?: 0L
                val currentKasbonTerbayar = (statsSnap.get("kasbonTerbayar") as? Number)?.toLong() ?: 0L
                val currentVolumePembelian = (statsSnap.get("volumePembelian") as? Number)?.toLong() ?: 0L

                var newAktifKasbon = currentAktifKasbon
                var newUangKeluar = currentUangKeluar
                var newKasbonTerbayar = currentKasbonTerbayar
                var newVolumePembelian = currentVolumePembelian

                newAktifKasbon = (newAktifKasbon + sisaKasbonChange.toLong()).coerceAtLeast(0L)

                if (transaction.jenis == TransactionType.KASBON_BARU) {
                    newUangKeluar += transaction.jumlah
                }
                
                if (transaction.jenis == TransactionType.BAYAR) {
                    newKasbonTerbayar += kotlin.math.abs(transaction.jumlah)
                }

                if (transaction.jenis == TransactionType.SETOR) {
                    newUangKeluar += kotlin.math.abs(transaction.jumlah)
                    newVolumePembelian += weight.toLong()
                }

                // === 3. WRITE ALL DOCUMENTS ===
                // Write to subcollection
                val transactionRef = farmerRef.collection("transactions").document(transaction.id)
                val transactionData = mapOf(
                    "jenis" to transaction.jenis.name,
                    "deskripsi" to transaction.deskripsi,
                    "tanggal" to transaction.tanggal,
                    "jumlah" to transaction.jumlah,
                    "label" to transaction.label,
                    "farmerName" to farmerName,
                    "timestamp" to System.currentTimeMillis()
                )

                // Write to subcollection
                firestoreTransaction.set(transactionRef, transactionData)

                // Juga simpan di log global agar laporan bisa menampilkan semua transaksi
                val logRef = firestore.collection("users").document(uid).collection("transactions_log").document(transaction.id)
                firestoreTransaction.set(logRef, transactionData)

                // Update parent farmer stats
                val farmerUpdates = mapOf(
                    "totalDisetor" to newDisetor,
                    "sisaKasbon" to newKasbon
                )
                firestoreTransaction.update(farmerRef, farmerUpdates)

                // Update global stats
                val statsUpdates = mapOf(
                    "aktifKasbon" to newAktifKasbon,
                    "uangKeluar" to newUangKeluar,
                    "kasbonTerbayar" to newKasbonTerbayar,
                    "volumePembelian" to newVolumePembelian
                )
                if (statsSnap.exists()) {
                    firestoreTransaction.update(statsRef, statsUpdates)
                } else {
                    val initialStats = mapOf(
                        "totalStokBasah" to 0.0,
                        "totalStokKering" to 0.0,
                        "penyusutan" to 0.0,
                        "aktifKasbon" to newAktifKasbon,
                        "uangKeluar" to newUangKeluar,
                        "kasbonTerbayar" to newKasbonTerbayar,
                        "stokTerjual" to 0L,
                        "estLabaKotor" to 0L,
                        "volumePembelian" to newVolumePembelian,
                        "hargaGradeA" to 12000L,
                        "hargaGradeB" to 9500L,
                        "hargaGradeC" to 7000L
                    )
                    firestoreTransaction.set(statsRef, initialStats)
                }
            }.await()
            
            // Send WA Notification
            try {
                val farmerRef = firestore.collection("users").document(uid).collection("farmers").document(farmerId)
                val farmerSnap = farmerRef.get().await()
                val nomorPetani = farmerSnap.getString("nomorTelepon") ?: ""
                val farmerName = farmerSnap.getString("nama") ?: "Petani"
                val newKasbon = (farmerSnap.get("sisaKasbon") as? Number)?.toDouble() ?: 0.0

                val notaDigital = """
                    *NOTA DIGITAL PENGEPUL PINANG*
                    -----------------------------------
                    Halo Bapak/Ibu *$farmerName*,
                    Berikut adalah rincian transaksi Anda:
                    
                    *Jenis*: ${transaction.jenis.name}
                    *Tanggal*: ${transaction.tanggal}
                    *Keterangan*: ${transaction.deskripsi}
                    *Nominal*: Rp. ${kotlin.math.abs(transaction.jumlah)}
                    
                    *Sisa Hutang/Kasbon Saat Ini*: Rp$newKasbon
                    -----------------------------------
                    _Terima kasih telah bermitra dengan kami!_
                """.trimIndent()

                whatsappHelper.sendNotification(nomorPetani, notaDigital)
            } catch (e: Exception) {
                android.util.Log.e("FarmerRepositoryImpl", "Gagal mengirim nota WA: ${e.message}")
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getGlobalTransactions(): Flow<List<FarmerTransaction>> = callbackFlow {
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            close(Exception("Belum login"))
            return@callbackFlow
        }
        val listener = firestore.collection("users").document(uid).collection("transactions_log")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        mapDocToTransaction(doc.id, doc.data)
                    }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }

    private fun mapDocToFarmer(id: String, data: Map<String, Any>?): Farmer {
        if (data == null) return Farmer(id, "", "", "", 0.0, "", false)
        return Farmer(
            id = id,
            nama = data["nama"] as? String ?: "",
            nomorTelepon = data["nomorTelepon"] as? String ?: "",
            alamat = data["alamat"] as? String ?: "",
            luasLahan = (data["luasLahan"] as? Number)?.toDouble() ?: 0.0,
            jenisTanaman = data["jenisTanaman"] as? String ?: "",
            statusAktif = data["statusAktif"] as? Boolean ?: false,
            fotoUrl = data["fotoUrl"] as? String,
            totalDisetor = (data["totalDisetor"] as? Number)?.toDouble() ?: 0.0,
            sisaKasbon = (data["sisaKasbon"] as? Number)?.toDouble() ?: 0.0
        )
    }

    private fun mapDocToTransaction(id: String, data: Map<String, Any>?): FarmerTransaction {
        if (data == null) return FarmerTransaction(id, TransactionType.SETOR, "", "", 0, "")
        val jenisStr = data["jenis"] as? String ?: "SETOR"
        val jenis = try { TransactionType.valueOf(jenisStr) } catch(e: Exception) { TransactionType.SETOR }
        return FarmerTransaction(
            id = id,
            jenis = jenis,
            deskripsi = data["deskripsi"] as? String ?: "",
            tanggal = data["tanggal"] as? String ?: "",
            jumlah = (data["jumlah"] as? Number)?.toLong() ?: 0L,
            label = data["label"] as? String ?: "",
            farmerName = data["farmerName"] as? String,
            timestamp = (data["timestamp"] as? Number)?.toLong() ?: 0L
        )
    }
}
