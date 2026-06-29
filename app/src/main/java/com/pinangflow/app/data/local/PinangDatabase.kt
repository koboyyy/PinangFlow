package com.pinangflow.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StockEntryEntity::class], version = 1, exportSchema = false)
abstract class PinangDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
}
