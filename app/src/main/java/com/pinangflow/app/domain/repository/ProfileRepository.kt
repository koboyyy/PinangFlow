package com.pinangflow.app.domain.repository

import com.pinangflow.app.domain.model.PengepulProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<PengepulProfile>
    suspend fun updateProfile(profile: PengepulProfile): Result<Unit>
}
