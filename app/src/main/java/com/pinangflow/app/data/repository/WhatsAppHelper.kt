package com.pinangflow.app.data.repository

import android.util.Log
import com.pinangflow.app.BuildConfig
import com.pinangflow.app.data.remote.WhatsAppApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WhatsAppHelper @Inject constructor(
    private val apiService: WhatsAppApiService
) {
    suspend fun sendNotification(nomorWa: String, pesan: String, customToken: String? = null) {
        // Jalankan di thread I/O agar tidak memblokir UI
        withContext(Dispatchers.IO) {
            try {
                if (nomorWa.isBlank()) return@withContext
                
                val usedToken = customToken?.takeIf { it.isNotBlank() } ?: BuildConfig.WA_GATEWAY_TOKEN
                
                val response = apiService.sendMessage(
                    token = usedToken,
                    target = nomorWa,
                    message = pesan
                )
                
                if (!response.isSuccessful) {
                    Log.e("WhatsAppHelper", "Gagal mengirim pesan: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                // Error handling (misal: API down, tidak ada internet)
                // Kita tangkap error di sini agar aplikasi tidak crash
                Log.e("WhatsAppHelper", "Error koneksi WhatsApp Gateway: ${e.localizedMessage}")
            }
        }
    }

    suspend fun testConnection(nomorWa: String, customToken: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                if (nomorWa.isBlank()) return@withContext Result.failure(Exception("Nomor WA kosong"))
                if (customToken.isBlank()) return@withContext Result.failure(Exception("Token kosong"))
                
                val response = apiService.sendMessage(
                    token = customToken,
                    target = nomorWa,
                    message = "Halo! Ini adalah pesan tes dari aplikasi PinangFlow untuk memastikan token Fonnte Anda aktif."
                )
                
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Gagal: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
