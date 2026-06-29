package com.pinangflow.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.pinangflow.app.domain.model.Grade
import com.pinangflow.app.domain.model.HarvestCondition
import com.pinangflow.app.domain.model.StockEntry
import com.pinangflow.app.domain.model.StockStatus
import com.pinangflow.app.domain.model.Transaction
import com.pinangflow.app.domain.repository.StockRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : StockRepository {

    override suspend fun addStockEntry(entry: StockEntry): Result<Unit> {
        return try {
            val data = mapOf(
                "petaniId" to entry.petaniId,
                "initialWeightKg" to entry.initialWeightKg,
                "currentWeightKg" to entry.currentWeightKg,
                "grade" to entry.grade.name,
                "condition" to entry.condition.name,
                "buyPricePerKg" to entry.buyPricePerKg,
                "entryTimestamp" to entry.entryTimestamp,
                "status" to entry.status.name,
                "dailyShrinkagePercentage" to entry.dailyShrinkagePercentage
            )
            firestore.collection("stock_entries").document(entry.id).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAllStockEntries(): Flow<List<StockEntry>> = callbackFlow {
        val listener = firestore.collection("stock_entries")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        mapDocToStockEntry(doc.id, doc.data)
                    }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }

    override fun getDryingStockEntries(): Flow<List<StockEntry>> = callbackFlow {
        val listener = firestore.collection("stock_entries")
            .whereEqualTo("status", "DRYING")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        mapDocToStockEntry(doc.id, doc.data)
                    }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }

    override fun getReadyStockEntries(): Flow<List<StockEntry>> = callbackFlow {
        val listener = firestore.collection("stock_entries")
            .whereEqualTo("status", "READY")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        mapDocToStockEntry(doc.id, doc.data)
                    }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }

    override suspend fun updateStockWeightAndStatus(
        entryId: String,
        currentWeight: Double,
        newStatus: String
    ): Result<Unit> {
        return try {
            firestore.collection("stock_entries").document(entryId)
                .update(
                    mapOf(
                        "currentWeightKg" to currentWeight,
                        "status" to newStatus
                    )
                ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun recordTransaction(transaction: Transaction): Result<Unit> {
        return try {
            val data = mapOf(
                "buyerName" to transaction.buyerName,
                "totalWeightKg" to transaction.totalWeightKg,
                "sellPricePerKg" to transaction.sellPricePerKg,
                "timestamp" to transaction.timestamp,
                "stockEntryIds" to transaction.stockEntryIds
            )
            firestore.collection("transactions").document(transaction.id).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getTransactions(): Flow<List<Transaction>> = callbackFlow {
        val listener = firestore.collection("transactions")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = snapshot.documents.map { doc ->
                        val stockEntryIds = doc["stockEntryIds"] as? List<String> ?: emptyList()
                        Transaction(
                            id = doc.id,
                            buyerName = doc["buyerName"] as? String ?: "",
                            totalWeightKg = (doc["totalWeightKg"] as? Number)?.toDouble() ?: 0.0,
                            sellPricePerKg = (doc["sellPricePerKg"] as? Number)?.toDouble() ?: 0.0,
                            timestamp = (doc["timestamp"] as? Number)?.toLong() ?: 0L,
                            stockEntryIds = stockEntryIds
                        )
                    }
                    trySend(list)
                }
            }
        awaitClose { listener.remove() }
    }

    private fun mapDocToStockEntry(id: String, data: Map<String, Any>?): StockEntry {
        if (data == null) return StockEntry(id, "", 0.0, 0.0, Grade.A, HarvestCondition.BASAH, 0.0, 0L, StockStatus.DRYING, 2.5)
        val gradeStr = data["grade"] as? String ?: "A"
        val grade = try { Grade.valueOf(gradeStr) } catch(e: Exception) { Grade.A }
        val condStr = data["condition"] as? String ?: "BASAH"
        val condition = try { HarvestCondition.valueOf(condStr) } catch(e: Exception) { HarvestCondition.BASAH }
        val statusStr = data["status"] as? String ?: "DRYING"
        val status = try { StockStatus.valueOf(statusStr) } catch(e: Exception) { StockStatus.DRYING }
        return StockEntry(
            id = id,
            petaniId = data["petaniId"] as? String ?: "",
            initialWeightKg = (data["initialWeightKg"] as? Number)?.toDouble() ?: 0.0,
            currentWeightKg = (data["currentWeightKg"] as? Number)?.toDouble() ?: 0.0,
            grade = grade,
            condition = condition,
            buyPricePerKg = (data["buyPricePerKg"] as? Number)?.toDouble() ?: 0.0,
            entryTimestamp = (data["entryTimestamp"] as? Number)?.toLong() ?: 0L,
            status = status,
            dailyShrinkagePercentage = (data["dailyShrinkagePercentage"] as? Number)?.toDouble() ?: 2.5
        )
    }
}
