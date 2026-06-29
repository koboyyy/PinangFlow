package com.pinangflow.app.domain.repository

import com.pinangflow.app.domain.model.StockEntry
import com.pinangflow.app.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun addStockEntry(entry: StockEntry): Result<Unit>
    fun getAllStockEntries(): Flow<List<StockEntry>>
    fun getDryingStockEntries(): Flow<List<StockEntry>>
    fun getReadyStockEntries(): Flow<List<StockEntry>>
    
    suspend fun updateStockWeightAndStatus(entryId: String, currentWeight: Double, newStatus: String): Result<Unit>
    
    suspend fun recordTransaction(transaction: Transaction): Result<Unit>
    fun getTransactions(): Flow<List<Transaction>>
}
