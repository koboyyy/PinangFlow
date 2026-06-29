package com.pinangflow.app.di

import com.pinangflow.app.data.repository.StockRepositoryImpl
import com.pinangflow.app.data.repository.FarmerRepositoryImpl
import com.pinangflow.app.data.repository.BatchRepositoryImpl
import com.pinangflow.app.data.repository.AuthRepositoryImpl
import com.pinangflow.app.domain.repository.StockRepository
import com.pinangflow.app.domain.repository.FarmerRepository
import com.pinangflow.app.domain.repository.BatchRepository
import com.pinangflow.app.domain.repository.AuthRepository
import com.pinangflow.app.data.repository.ExportRepositoryImpl
import com.pinangflow.app.domain.repository.ExportRepository
import com.pinangflow.app.data.repository.ProfileRepositoryImpl
import com.pinangflow.app.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

    @Binds
    @Singleton
    abstract fun bindFarmerRepository(
        farmerRepositoryImpl: FarmerRepositoryImpl
    ): FarmerRepository

    @Binds
    @Singleton
    abstract fun bindBatchRepository(
        batchRepositoryImpl: BatchRepositoryImpl
    ): BatchRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindExportRepository(
        exportRepositoryImpl: ExportRepositoryImpl
    ): ExportRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository
}

