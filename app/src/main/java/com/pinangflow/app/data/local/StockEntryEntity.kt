package com.pinangflow.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_entries")
data class StockEntryEntity(
    @PrimaryKey val id: String,
    val petaniId: String,
    val initialWeightKg: Double,
    val currentWeightKg: Double,
    val grade: String,
    val condition: String,
    val buyPricePerKg: Double,
    val entryTimestamp: Long,
    val status: String,
    val dailyShrinkagePercentage: Double = 2.5
)
