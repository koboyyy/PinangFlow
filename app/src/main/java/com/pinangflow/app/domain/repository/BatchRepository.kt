package com.pinangflow.app.domain.repository

import com.pinangflow.app.domain.model.Batch
import kotlinx.coroutines.flow.Flow

interface BatchRepository {
    fun getBatches(): Flow<List<Batch>>
    fun getBatch(batchId: String): Flow<Batch?>
    suspend fun addBatch(batch: Batch): Result<Unit>
    suspend fun updateBatchDryWeight(batchId: String, dryWeight: Double): Result<Unit>
    fun getLaporanStats(): Flow<Map<String, Any>>
    suspend fun updateDailyPrices(hargaGradeA: Long, hargaGradeB: Long, hargaGradeC: Long): Result<Unit>
}
