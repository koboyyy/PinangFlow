package com.pinangflow.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinangflow.app.domain.model.User
import com.pinangflow.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val currentUser: StateFlow<User?> = authRepository.getCurrentUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun isLoggedIn(): Boolean = authRepository.isLoggedIn()

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            authRepository.loginWithEmail(email, password)
                .onSuccess { user ->
                    _authState.value = AuthState.Success(user)
                }
                .onFailure { error ->
                    _authState.value = AuthState.Error(
                        mapFirebaseError(error)
                    )
                }
        }
    }

    fun register(email: String, password: String, name: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            authRepository.registerWithEmail(email, password, name)
                .onSuccess { user ->
                    _authState.value = AuthState.Success(user)
                }
                .onFailure { error ->
                    _authState.value = AuthState.Error(
                        mapFirebaseError(error)
                    )
                }
        }
    }

    fun signInWithGoogle(idToken: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            authRepository.signInWithGoogle(idToken)
                .onSuccess { user ->
                    _authState.value = AuthState.Success(user)
                }
                .onFailure { error ->
                    _authState.value = AuthState.Error(
                        mapFirebaseError(error)
                    )
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _authState.value = AuthState.Idle
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }

    private fun mapFirebaseError(error: Throwable): String {
        val msg = error.message ?: "Terjadi kesalahan"
        return when {
            msg.contains("INVALID_LOGIN_CREDENTIALS", ignoreCase = true) ||
            msg.contains("invalid credential", ignoreCase = true) ||
            msg.contains("password is invalid", ignoreCase = true) ||
            msg.contains("no user record", ignoreCase = true) ->
                "Email atau password salah!"

            msg.contains("email address is already in use", ignoreCase = true) ->
                "Email sudah terdaftar! Silakan login."

            msg.contains("email address is badly formatted", ignoreCase = true) ->
                "Format email tidak valid!"

            msg.contains("password should be at least", ignoreCase = true) ||
            msg.contains("weak password", ignoreCase = true) ->
                "Password terlalu lemah! Minimal 6 karakter."

            msg.contains("network error", ignoreCase = true) ||
            msg.contains("network", ignoreCase = true) ->
                "Tidak ada koneksi internet!"

            msg.contains("too many requests", ignoreCase = true) ->
                "Terlalu banyak percobaan! Coba lagi nanti."

            else -> msg
        }
    }
}
