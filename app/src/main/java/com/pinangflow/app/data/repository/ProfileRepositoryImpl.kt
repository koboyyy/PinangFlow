package com.pinangflow.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.pinangflow.app.domain.model.PengepulProfile
import com.pinangflow.app.domain.repository.ProfileRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ProfileRepository {

    override fun getProfile(): Flow<PengepulProfile> = callbackFlow {
        val currentUser = firebaseAuth.currentUser
        val uid = currentUser?.uid

        if (uid == null) {
            close(Exception("Belum login"))
            return@callbackFlow
        }

        val listener = firestore.collection("users").document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val profile = PengepulProfile(
                        uid = uid,
                        email = currentUser.email ?: "",
                        namaLapak = snapshot.getString("namaLapak") ?: snapshot.getString("nama") ?: "",
                        namaPemilik = snapshot.getString("namaPemilik") ?: "",
                        alamatGudang = snapshot.getString("alamatGudang") ?: "",
                        nomorWaLaporan = snapshot.getString("nomorWaLaporan") ?: "",
                        tokenWaGateway = snapshot.getString("tokenWaGateway") ?: ""
                    )
                    trySend(profile)
                } else {
                    // Jika dokumen tidak ada, kembalikan objek default
                    trySend(PengepulProfile(uid = uid, email = currentUser.email ?: ""))
                }
            }

        awaitClose { listener.remove() }
    }

    override suspend fun updateProfile(profile: PengepulProfile): Result<Unit> {
        return try {
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("Akses ditolak: Multi-Tenant invalid.")

            // Validasi Nomor WA (Ganti 08 menjadi 628)
            var formattedWa = profile.nomorWaLaporan.trim()
            if (formattedWa.startsWith("0")) {
                formattedWa = "62" + formattedWa.substring(1)
            } else if (formattedWa.startsWith("+62")) {
                formattedWa = formattedWa.substring(1) // hilangkan plus
            }

            // HANYA update kolom yang diizinkan (uid dan email tidak ikut di-update)
            val updates = mapOf(
                "namaLapak" to profile.namaLapak,
                "namaPemilik" to profile.namaPemilik,
                "alamatGudang" to profile.alamatGudang,
                "nomorWaLaporan" to formattedWa,
                "tokenWaGateway" to profile.tokenWaGateway
            )

            firestore.collection("users").document(uid)
                .set(updates, SetOptions.merge())
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
