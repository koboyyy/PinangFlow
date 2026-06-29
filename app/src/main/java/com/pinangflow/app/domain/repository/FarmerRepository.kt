package com.pinangflow.app.domain.repository

import com.pinangflow.app.domain.model.Farmer
import com.pinangflow.app.domain.model.FarmerTransaction
import kotlinx.coroutines.flow.Flow

interface FarmerRepository {
    fun getFarmers(): Flow<List<Farmer>>
    fun getFarmer(farmerId: String): Flow<Farmer?>
    suspend fun addFarmer(farmer: Farmer): Result<Unit>
    fun getFarmerTransactions(farmerId: String): Flow<List<FarmerTransaction>>
    suspend fun addTransaction(farmerId: String, transaction: FarmerTransaction): Result<Unit>
    fun getGlobalTransactions(): Flow<List<FarmerTransaction>>
}
