package com.pinangflow.app.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH'J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH'J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H'J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H'J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH'\u00a8\u0006\u001b"}, d2 = {"Lcom/pinangflow/app/di/RepositoryModule;", "", "()V", "bindAuthRepository", "Lcom/pinangflow/app/domain/repository/AuthRepository;", "authRepositoryImpl", "Lcom/pinangflow/app/data/repository/AuthRepositoryImpl;", "bindBatchRepository", "Lcom/pinangflow/app/domain/repository/BatchRepository;", "batchRepositoryImpl", "Lcom/pinangflow/app/data/repository/BatchRepositoryImpl;", "bindExportRepository", "Lcom/pinangflow/app/domain/repository/ExportRepository;", "exportRepositoryImpl", "Lcom/pinangflow/app/data/repository/ExportRepositoryImpl;", "bindFarmerRepository", "Lcom/pinangflow/app/domain/repository/FarmerRepository;", "farmerRepositoryImpl", "Lcom/pinangflow/app/data/repository/FarmerRepositoryImpl;", "bindProfileRepository", "Lcom/pinangflow/app/domain/repository/ProfileRepository;", "profileRepositoryImpl", "Lcom/pinangflow/app/data/repository/ProfileRepositoryImpl;", "bindStockRepository", "Lcom/pinangflow/app/domain/repository/StockRepository;", "stockRepositoryImpl", "Lcom/pinangflow/app/data/repository/StockRepositoryImpl;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.pinangflow.app.domain.repository.StockRepository bindStockRepository(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.data.repository.StockRepositoryImpl stockRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.pinangflow.app.domain.repository.FarmerRepository bindFarmerRepository(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.data.repository.FarmerRepositoryImpl farmerRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.pinangflow.app.domain.repository.BatchRepository bindBatchRepository(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.data.repository.BatchRepositoryImpl batchRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.pinangflow.app.domain.repository.AuthRepository bindAuthRepository(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.data.repository.AuthRepositoryImpl authRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.pinangflow.app.domain.repository.ExportRepository bindExportRepository(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.data.repository.ExportRepositoryImpl exportRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.pinangflow.app.domain.repository.ProfileRepository bindProfileRepository(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.data.repository.ProfileRepositoryImpl profileRepositoryImpl);
}