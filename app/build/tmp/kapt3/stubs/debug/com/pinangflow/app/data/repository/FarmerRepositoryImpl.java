package com.pinangflow.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ$\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ,\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0018\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00182\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u001c\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u001a0\u00182\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0014\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a0\u0018H\u0016J\u0014\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u001a0\u0018H\u0016J&\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u00122\u0014\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020!\u0018\u00010 H\u0002J&\u0010\"\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u00122\u0014\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020!\u0018\u00010 H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006#"}, d2 = {"Lcom/pinangflow/app/data/repository/FarmerRepositoryImpl;", "Lcom/pinangflow/app/domain/repository/FarmerRepository;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "firebaseAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "whatsappHelper", "Lcom/pinangflow/app/data/repository/WhatsAppHelper;", "(Lcom/google/firebase/firestore/FirebaseFirestore;Lcom/google/firebase/auth/FirebaseAuth;Lcom/pinangflow/app/data/repository/WhatsAppHelper;)V", "addFarmer", "Lkotlin/Result;", "", "farmer", "Lcom/pinangflow/app/domain/model/Farmer;", "addFarmer-gIAlu-s", "(Lcom/pinangflow/app/domain/model/Farmer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addTransaction", "farmerId", "", "transaction", "Lcom/pinangflow/app/domain/model/FarmerTransaction;", "addTransaction-0E7RQCE", "(Ljava/lang/String;Lcom/pinangflow/app/domain/model/FarmerTransaction;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFarmer", "Lkotlinx/coroutines/flow/Flow;", "getFarmerTransactions", "", "getFarmers", "getGlobalTransactions", "mapDocToFarmer", "id", "data", "", "", "mapDocToTransaction", "app_debug"})
public final class FarmerRepositoryImpl implements com.pinangflow.app.domain.repository.FarmerRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth firebaseAuth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.data.repository.WhatsAppHelper whatsappHelper = null;
    
    @javax.inject.Inject()
    public FarmerRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore, @org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth firebaseAuth, @org.jetbrains.annotations.NotNull()
    com.pinangflow.app.data.repository.WhatsAppHelper whatsappHelper) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.domain.model.Farmer>> getFarmers() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.pinangflow.app.domain.model.Farmer> getFarmer(@org.jetbrains.annotations.NotNull()
    java.lang.String farmerId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.domain.model.FarmerTransaction>> getFarmerTransactions(@org.jetbrains.annotations.NotNull()
    java.lang.String farmerId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.domain.model.FarmerTransaction>> getGlobalTransactions() {
        return null;
    }
    
    private final com.pinangflow.app.domain.model.Farmer mapDocToFarmer(java.lang.String id, java.util.Map<java.lang.String, ? extends java.lang.Object> data) {
        return null;
    }
    
    private final com.pinangflow.app.domain.model.FarmerTransaction mapDocToTransaction(java.lang.String id, java.util.Map<java.lang.String, ? extends java.lang.Object> data) {
        return null;
    }
}