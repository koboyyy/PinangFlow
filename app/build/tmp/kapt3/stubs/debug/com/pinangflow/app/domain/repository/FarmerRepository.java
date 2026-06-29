package com.pinangflow.app.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0007\u0010\bJ,\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0018\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00112\u0006\u0010\n\u001a\u00020\u000bH&J\u001c\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00130\u00112\u0006\u0010\n\u001a\u00020\u000bH&J\u0014\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00130\u0011H&J\u0014\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00130\u0011H&\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0016"}, d2 = {"Lcom/pinangflow/app/domain/repository/FarmerRepository;", "", "addFarmer", "Lkotlin/Result;", "", "farmer", "Lcom/pinangflow/app/domain/model/Farmer;", "addFarmer-gIAlu-s", "(Lcom/pinangflow/app/domain/model/Farmer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addTransaction", "farmerId", "", "transaction", "Lcom/pinangflow/app/domain/model/FarmerTransaction;", "addTransaction-0E7RQCE", "(Ljava/lang/String;Lcom/pinangflow/app/domain/model/FarmerTransaction;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFarmer", "Lkotlinx/coroutines/flow/Flow;", "getFarmerTransactions", "", "getFarmers", "getGlobalTransactions", "app_debug"})
public abstract interface FarmerRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.domain.model.Farmer>> getFarmers();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.pinangflow.app.domain.model.Farmer> getFarmer(@org.jetbrains.annotations.NotNull()
    java.lang.String farmerId);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.domain.model.FarmerTransaction>> getFarmerTransactions(@org.jetbrains.annotations.NotNull()
    java.lang.String farmerId);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.domain.model.FarmerTransaction>> getGlobalTransactions();
}