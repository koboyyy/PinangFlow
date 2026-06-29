package com.pinangflow.app.di

import android.content.Context
import androidx.room.Room
import com.pinangflow.app.data.local.PinangDatabase
import com.pinangflow.app.data.local.StockDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PinangDatabase {
        return Room.databaseBuilder(
            context,
            PinangDatabase::class.java,
            "pinang_flow.db"
        ).build()
    }

    @Provides
    fun provideStockDao(database: PinangDatabase): StockDao {
        return database.stockDao()
    }
}
