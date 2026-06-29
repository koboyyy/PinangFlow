package com.pinangflow.app.domain.model

enum class BidStatus { PENDING, ACCEPTED, REJECTED }

data class Bid(
    val id: String,
    val postId: String,
    val pengepulId: String,
    val bidPricePerKg: Double,
    val timestamp: Long,
    val status: BidStatus
)
