package com.pinangflow.app.domain.model

data class Transaction(
    val id: String,
    val buyerName: String,
    val totalWeightKg: Double,
    val sellPricePerKg: Double,
    val timestamp: Long,
    val stockEntryIds: List<String> // References to the stock entries used for this sale
)
