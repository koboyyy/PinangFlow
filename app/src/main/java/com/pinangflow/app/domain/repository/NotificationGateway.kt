package com.pinangflow.app.domain.repository

interface NotificationGateway {
    suspend fun sendMessage(phoneNumber: String, message: String): Result<Unit>
}
