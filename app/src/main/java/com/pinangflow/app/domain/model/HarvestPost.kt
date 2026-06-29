package com.pinangflow.app.domain.model

enum class HarvestType { BULAT, BELAH }
enum class HarvestCondition { BASAH, KERING }
enum class PostStatus { POSTED, NEGOTIATING, ACCEPTED, IN_PICKUP, COMPLETED }

data class HarvestPost(
    val id: String,
    val petaniId: String,
    val type: HarvestType,
    val estimatedWeightKg: Double,
    val condition: HarvestCondition,
    val notes: String,
    val photoUrl: String?,
    val timestamp: Long,
    val status: PostStatus
)
