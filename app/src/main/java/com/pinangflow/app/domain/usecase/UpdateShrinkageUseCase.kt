package com.pinangflow.app.domain.usecase

import com.pinangflow.app.domain.model.StockStatus
import com.pinangflow.app.domain.repository.StockRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateShrinkageUseCase @Inject constructor(
    private val stockRepository: StockRepository,
    private val calculateShrinkageUseCase: CalculateShrinkageUseCase
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            val dryingStocks = stockRepository.getDryingStockEntries().first()
            val currentTime = System.currentTimeMillis()
            val millisInDay = 24 * 60 * 60 * 1000L

            for (stock in dryingStocks) {
                val daysDrying = ((currentTime - stock.entryTimestamp) / millisInDay).toInt()
                if (daysDrying > 0) {
                    val newWeight = calculateShrinkageUseCase(
                        initialWeightKg = stock.initialWeightKg,
                        dailyShrinkagePercentage = stock.dailyShrinkagePercentage,
                        daysDrying = daysDrying
                    )
                    
                    // Simple logic: if weight is stable or days > some threshold, it might be ready. 
                    // We'll just update the weight for now and keep it DRYING unless manually changed.
                    stockRepository.updateStockWeightAndStatus(
                        entryId = stock.id,
                        currentWeight = newWeight,
                        newStatus = stock.status.name
                    )
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
