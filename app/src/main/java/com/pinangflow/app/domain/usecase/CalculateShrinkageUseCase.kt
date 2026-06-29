package com.pinangflow.app.domain.usecase

import javax.inject.Inject
import kotlin.math.pow

class CalculateShrinkageUseCase @Inject constructor() {
    
    /**
     * Calculates the estimated current weight based on the initial weight, 
     * daily shrinkage percentage, and number of days drying.
     * 
     * Uses compound shrinkage formula: Final Weight = Initial Weight * (1 - Percentage/100)^Days
     */
    operator fun invoke(
        initialWeightKg: Double,
        dailyShrinkagePercentage: Double,
        daysDrying: Int,
        isCompound: Boolean = true
    ): Double {
        if (initialWeightKg <= 0 || daysDrying < 0 || dailyShrinkagePercentage < 0) {
            return initialWeightKg
        }

        return if (isCompound) {
            val factor = 1.0 - (dailyShrinkagePercentage / 100.0)
            initialWeightKg * factor.pow(daysDrying.toDouble())
        } else {
            // Linear logic from SRS
            val totalShrinkage = initialWeightKg * (dailyShrinkagePercentage / 100.0) * daysDrying
            val estimated = initialWeightKg - totalShrinkage
            if (estimated < 0) 0.0 else estimated
        }
    }
}
