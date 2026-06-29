package com.pinangflow.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinangflow.app.domain.model.PengepulProfile
import com.pinangflow.app.domain.repository.ProfileRepository
import com.pinangflow.app.data.repository.WhatsAppHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val whatsAppHelper: WhatsAppHelper
) : ViewModel() {

    val profile: StateFlow<PengepulProfile?> = profileRepository.getProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun updateProfile(
        updatedProfile: PengepulProfile,
        onSuccess: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            profileRepository.updateProfile(updatedProfile)
                .onSuccess { onSuccess() }
                .onFailure { onFailure(it) }
        }
    }

    fun testWhatsAppConnection(
        nomorWa: String,
        token: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            whatsAppHelper.testConnection(nomorWa, token)
                .onSuccess {
                    onResult(true, "Pesan berhasil dikirim! Silakan cek WhatsApp Anda.")
                }
                .onFailure { error ->
                    onResult(false, error.message ?: "Gagal mengirim pesan")
                }
        }
    }
}
