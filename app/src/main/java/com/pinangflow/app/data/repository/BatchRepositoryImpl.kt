package com.pinangflow.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pinangflow.app.domain.model.Batch
import com.pinangflow.app.domain.model.BatchStatus
import com.pinangflow.app.domain.repository.BatchRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

import com.google.firebase.auth.FirebaseAuth
import com.pinangflow.app.domain.repository.FarmerRepository
import kotlinx.coroutines.flow.first

class BatchRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val whatsappHelper: WhatsAppHelper,
    private val farmerRepository: FarmerRepository
) : BatchRepository {

    override fun getBatches(): Flow<List<Batch>> = callbackFlow {
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            close(Exception("Belum login"))
            return@callbackFlow
        }
        val listener = firestore.collection("users").document(uid).collection("batches")
            .orderBy("nomorBatch", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        mapDocToBatch(doc.id, doc.data)
                    }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }

    override fun getBatch(batchId: String): Flow<Batch?> = callbackFlow {
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            close(Exception("Belum login"))
            return@callbackFlow
        }
        val listener = firestore.collection("users").document(uid).collection("batches").document(batchId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    trySend(if (snapshot.exists()) mapDocToBatch(snapshot.id, snapshot.data) else null)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addBatch(batch: Batch): Result<Unit> {
        return try {
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("Belum login")
            firestore.runTransaction { transaction ->
                val statsRef = firestore.collection("users").document(uid).collection("stats").document("global")
                val statsSnap = transaction.get(statsRef)

                val batchRef = firestore.collection("users").document(uid).collection("batches").document(batch.id)
                val batchData = mapOf(
                    "nomorBatch" to batch.nomorBatch,
                    "tanggalMasuk" to batch.tanggalMasuk,
                    "tanggalSelesai" to batch.tanggalSelesai,
                    "beratBasah" to batch.beratBasah,
                    "beratKering" to batch.beratKering,
                    "status" to batch.status.name,
                    "areaJemur" to batch.areaJemur,
                    "suhu" to batch.suhu
                )
                transaction.set(batchRef, batchData)

                val currentBasah = (statsSnap.get("totalStokBasah") as? Number)?.toDouble() ?: 0.0
                val addBasahTons = batch.beratBasah / 1000.0
                val newBasah = currentBasah + addBasahTons

                val updates = mapOf("totalStokBasah" to newBasah)
                if (statsSnap.exists()) {
                    transaction.update(statsRef, updates)
                } else {
                    val initialStats = mapOf(
                        "totalStokBasah" to newBasah,
                        "totalStokKering" to 0.0,
                        "aktifKasbon" to 0.0,
                        "penyusutan" to 0.0,
                        "uangKeluar" to 0L,
                        "kasbonTerbayar" to 0L,
                        "stokTerjual" to 0L,
                        "estLabaKotor" to 0L,
                        "volumePembelian" to 0L,
                        "hargaGradeA" to 12000L,
                        "hargaGradeB" to 9500L,
                        "hargaGradeC" to 7000L
                    )
                    transaction.set(statsRef, initialStats)
                }
            }.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateBatchDryWeight(batchId: String, dryWeight: Double): Result<Unit> {
        return try {
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("Belum login")
            firestore.runTransaction { transaction ->
                val batchRef = firestore.collection("users").document(uid).collection("batches").document(batchId)
                val batchSnap = transaction.get(batchRef)
                if (!batchSnap.exists()) {
                    throw Exception("Batch tidak ditemukan!")
                }

                val beratBasah = (batchSnap.get("beratBasah") as? Number)?.toDouble() ?: 0.0
                val statusStr = batchSnap.get("status") as? String ?: ""

                if (statusStr != BatchStatus.SELESAI.name) {
                    val statsRef = firestore.collection("users").document(uid).collection("stats").document("global")
                    val statsSnap = transaction.get(statsRef)
                    val currentBasah = (statsSnap.get("totalStokBasah") as? Number)?.toDouble() ?: 0.0
                    val currentKering = (statsSnap.get("totalStokKering") as? Number)?.toDouble() ?: 0.0

                    val removeBasahTons = beratBasah / 1000.0
                    val addKeringTons = dryWeight / 1000.0

                    val newBasah = (currentBasah - removeBasahTons).coerceAtLeast(0.0)
                    val newKering = currentKering + addKeringTons

                    val shrinkage = if (beratBasah > 0) {
                        ((beratBasah - dryWeight) / beratBasah) * 100
                    } else 0.0

                    val updates = mapOf(
                        "totalStokBasah" to newBasah,
                        "totalStokKering" to newKering,
                        "penyusutan" to shrinkage
                    )

                    if (statsSnap.exists()) {
                        transaction.update(statsRef, updates)
                    } else {
                        val initialStats = mapOf(
                            "totalStokBasah" to newBasah,
                            "totalStokKering" to newKering,
                            "penyusutan" to shrinkage,
                            "aktifKasbon" to 0.0,
                            "uangKeluar" to 0L,
                            "kasbonTerbayar" to 0L,
                            "stokTerjual" to 0L,
                            "estLabaKotor" to 0L,
                            "volumePembelian" to 0L,
                            "hargaGradeA" to 12000L,
                            "hargaGradeB" to 9500L,
                            "hargaGradeC" to 7000L
                        )
                        transaction.set(statsRef, initialStats)
                    }
                }

                val batchUpdates = mapOf(
                    "beratKering" to dryWeight,
                    "status" to BatchStatus.SELESAI.name,
                    "tanggalSelesai" to "Hari Ini"
                )
                transaction.update(batchRef, batchUpdates)
            }.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getLaporanStats(): Flow<Map<String, Any>> = callbackFlow {
        val uid = firebaseAuth.currentUser?.uid
        if (uid == null) {
            close(Exception("Belum login"))
            return@callbackFlow
        }
        val listener = firestore.collection("users").document(uid).collection("stats").document("global")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    trySend(snapshot.data ?: emptyMap())
                } else {
                    trySend(
                        mapOf(
                            "totalStokBasah" to 0.0,
                            "totalStokKering" to 0.0,
                            "aktifKasbon" to 0.0,
                            "penyusutan" to 0.0,
                            "uangKeluar" to 0L,
                            "kasbonTerbayar" to 0L,
                            "stokTerjual" to 0L,
                            "estLabaKotor" to 0L,
                            "volumePembelian" to 0L,
                            "hargaGradeA" to 12000L,
                            "hargaGradeB" to 9500L,
                            "hargaGradeC" to 7000L
                        )
                    )
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun updateDailyPrices(hargaGradeA: Long, hargaGradeB: Long, hargaGradeC: Long): Result<Unit> {
        return try {
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("Belum login")
            val data = mapOf(
                "hargaGradeA" to hargaGradeA,
                "hargaGradeB" to hargaGradeB,
                "hargaGradeC" to hargaGradeC
            )
            firestore.collection("users").document(uid).collection("stats").document("global")
                .set(data, com.google.firebase.firestore.SetOptions.merge())
                .await()

            try {
                val semuaPetani = farmerRepository.getFarmers().first()
                val targetNumbers = semuaPetani
                    .filter { it.statusAktif && it.nomorTelepon.isNotBlank() }
                    .joinToString(",") { it.nomorTelepon }

                if (targetNumbers.isNotBlank()) {
                    val pesanBroadcast = """
                        📢 *INFO HARGA PINANG HARI INI*
                        -----------------------------------
                        Grade A: Rp$hargaGradeA
                        Grade B: Rp$hargaGradeB
                        Grade C: Rp$hargaGradeC
                        -----------------------------------
                        _Ayo segera setor hasil panen Anda!_
                    """.trimIndent()

                    whatsappHelper.sendNotification(targetNumbers, pesanBroadcast)
                }
            } catch (e: Exception) {
                android.util.Log.e("BatchRepositoryImpl", "Gagal mengirim broadcast WA: ${e.message}")
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun mapDocToBatch(id: String, data: Map<String, Any>?): Batch {
        if (data == null) return Batch(id, 0, "", null, 0.0, 0.0, BatchStatus.PROSES_PENGERINGAN, "", "")
        val nomorBatch = (data["nomorBatch"] as? Number)?.toInt() ?: 0
        val statusStr = data["status"] as? String ?: "PROSES_PENGERINGAN"
        val status = try { BatchStatus.valueOf(statusStr) } catch(e: Exception) { BatchStatus.PROSES_PENGERINGAN }
        return Batch(
            id = id,
            nomorBatch = nomorBatch,
            tanggalMasuk = data["tanggalMasuk"] as? String ?: "",
            tanggalSelesai = data["tanggalSelesai"] as? String,
            beratBasah = (data["beratBasah"] as? Number)?.toDouble() ?: 0.0,
            beratKering = (data["beratKering"] as? Number)?.toDouble() ?: 0.0,
            status = status,
            areaJemur = data["areaJemur"] as? String ?: "",
            suhu = data["suhu"] as? String ?: ""
        )
    }
}
