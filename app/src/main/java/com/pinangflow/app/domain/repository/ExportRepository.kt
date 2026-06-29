package com.pinangflow.app.domain.repository

import com.pinangflow.app.domain.model.ExportTransaction
import kotlinx.coroutines.flow.Flow

interface ExportRepository {
    fun getExportTransactions(): Flow<List<ExportTransaction>>
    suspend fun addExportTransaction(exportTransaction: ExportTransaction, ownerWaNumber: String): Result<String>
}
