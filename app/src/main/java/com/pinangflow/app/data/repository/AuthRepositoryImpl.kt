package com.pinangflow.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.pinangflow.app.domain.model.User
import com.pinangflow.app.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun getCurrentUser(): Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                trySend(
                    User(
                        uid = firebaseUser.uid,
                        email = firebaseUser.email ?: "",
                        displayName = firebaseUser.displayName ?: "Pengepul",
                        photoUrl = firebaseUser.photoUrl?.toString()
                    )
                )
            } else {
                trySend(null)
            }
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun loginWithEmail(email: String, password: String): Result<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("Login gagal!")
            val user = User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                displayName = firebaseUser.displayName ?: "Pengepul",
                photoUrl = firebaseUser.photoUrl?.toString()
            )
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerWithEmail(email: String, password: String, name: String): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: throw Exception("Registrasi gagal!")

            // Update display name
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            firebaseUser.updateProfile(profileUpdates).await()

            // Save user data to Firestore
            val userData = mapOf(
                "nama" to name,
                "email" to email,
                "role" to "PENGEPUL",
                "createdAt" to System.currentTimeMillis()
            )
            firestore.collection("users").document(firebaseUser.uid)
                .set(userData).await()

            val user = User(
                uid = firebaseUser.uid,
                email = email,
                displayName = name,
                photoUrl = null
            )
            initializeStatsIfEmpty(firebaseUser.uid)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = result.user ?: throw Exception("Google Sign-In gagal!")

            // Save user data to Firestore (merge to not overwrite)
            val userData = mapOf(
                "nama" to (firebaseUser.displayName ?: "Pengepul"),
                "email" to (firebaseUser.email ?: ""),
                "role" to "PENGEPUL",
                "photoUrl" to (firebaseUser.photoUrl?.toString() ?: ""),
                "lastLogin" to System.currentTimeMillis()
            )
            firestore.collection("users").document(firebaseUser.uid)
                .set(userData, com.google.firebase.firestore.SetOptions.merge()).await()

            val user = User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                displayName = firebaseUser.displayName ?: "Pengepul",
                photoUrl = firebaseUser.photoUrl?.toString()
            )
            initializeStatsIfEmpty(firebaseUser.uid)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    private suspend fun initializeStatsIfEmpty(uid: String) {
        try {
            val statsRef = firestore.collection("users").document(uid).collection("stats").document("global")
            val snap = statsRef.get().await()
            if (!snap.exists()) {
                val initialStats = mapOf(
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
                statsRef.set(initialStats).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
