package com.pinangflow.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockEntry(entry: StockEntryEntity)

    @Query("SELECT * FROM stock_entries")
    fun getAllStockEntries(): Flow<List<StockEntryEntity>>

    @Query("SELECT * FROM stock_entries WHERE status = 'DRYING'")
    fun getDryingStockEntries(): Flow<List<StockEntryEntity>>

    @Query("SELECT * FROM stock_entries WHERE status = 'READY' ORDER BY entryTimestamp ASC")
    fun getReadyStockEntriesAsc(): Flow<List<StockEntryEntity>>

    @Query("UPDATE stock_entries SET currentWeightKg = :currentWeight, status = :newStatus WHERE id = :entryId")
    suspend fun updateWeightAndStatus(entryId: String, currentWeight: Double, newStatus: String)
}
