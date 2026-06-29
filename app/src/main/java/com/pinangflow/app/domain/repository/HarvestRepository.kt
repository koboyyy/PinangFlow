package com.pinangflow.app.domain.repository

import com.pinangflow.app.domain.model.Bid
import com.pinangflow.app.domain.model.HarvestPost
import kotlinx.coroutines.flow.Flow

interface HarvestRepository {
    suspend fun createHarvestPost(post: HarvestPost): Result<Unit>
    fun getActivePostsByPetani(petaniId: String): Flow<List<HarvestPost>>
    fun getNearbyPosts(latitude: Double, longitude: Double, radiusKm: Double): Flow<List<HarvestPost>>
    
    suspend fun submitBid(bid: Bid): Result<Unit>
    fun getBidsForPost(postId: String): Flow<List<Bid>>
    
    suspend fun acceptBid(bidId: String): Result<Unit>
    suspend fun rejectBid(bidId: String): Result<Unit>
    
    suspend fun updatePostStatus(postId: String, newStatus: String): Result<Unit>
}
