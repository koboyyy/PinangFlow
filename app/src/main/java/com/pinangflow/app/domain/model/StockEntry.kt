package com.pinangflow.app.domain.model

enum class StockStatus { DRYING, READY, SOLD }
enum class Grade { A, B, C }

data class StockEntry(
    val id: String,
    val petaniId: String,
    val initialWeightKg: Double,
    val currentWeightKg: Double,
    val grade: Grade,
    val condition: HarvestCondition,
    val buyPricePerKg: Double,
    val entryTimestamp: Long,
    val status: StockStatus,
    val dailyShrinkagePercentage: Double = 2.5
)
