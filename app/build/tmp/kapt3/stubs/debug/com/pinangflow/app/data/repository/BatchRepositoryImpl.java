package com.pinangflow.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0005\u0018\u00002\u00020\u0001B'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ$\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0018\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00170\u0013H\u0016J\u001a\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u001a0\u00190\u0013H\u0016J&\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u00152\u0014\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0019H\u0002J,\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020 H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J4\u0010#\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%2\u0006\u0010'\u001a\u00020%H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b(\u0010)R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006*"}, d2 = {"Lcom/pinangflow/app/data/repository/BatchRepositoryImpl;", "Lcom/pinangflow/app/domain/repository/BatchRepository;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "firebaseAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "whatsappHelper", "Lcom/pinangflow/app/data/repository/WhatsAppHelper;", "farmerRepository", "Lcom/pinangflow/app/domain/repository/FarmerRepository;", "(Lcom/google/firebase/firestore/FirebaseFirestore;Lcom/google/firebase/auth/FirebaseAuth;Lcom/pinangflow/app/data/repository/WhatsAppHelper;Lcom/pinangflow/app/domain/repository/FarmerRepository;)V", "addBatch", "Lkotlin/Result;", "", "batch", "Lcom/pinangflow/app/domain/model/Batch;", "addBatch-gIAlu-s", "(Lcom/pinangflow/app/domain/model/Batch;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBatch", "Lkotlinx/coroutines/flow/Flow;", "batchId", "", "getBatches", "", "getLaporanStats", "", "", "mapDocToBatch", "id", "data", "updateBatchDryWeight", "dryWeight", "", "updateBatchDryWeight-0E7RQCE", "(Ljava/lang/String;DLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateDailyPrices", "hargaGradeA", "", "hargaGradeB", "hargaGradeC", "updateDailyPrices-BWLJW6A", "(JJJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class BatchRepositoryImpl implements com.pinangflow.app.domain.repository.BatchRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth firebaseAuth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.data.repository.WhatsAppHelper whatsappHelper = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.domain.repository.FarmerRepository farmerRepository = null;
    
    @javax.inject.Inject()
    public BatchRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore, @org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth firebaseAuth, @org.jetbrains.annotations.NotNull()
    com.pinangflow.app.data.repository.WhatsAppHelper whatsappHelper, @org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.repository.FarmerRepository farmerRepository) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.domain.model.Batch>> getBatches() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.pinangflow.app.domain.model.Batch> getBatch(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.Map<java.lang.String, java.lang.Object>> getLaporanStats() {
        return null;
    }
    
    private final com.pinangflow.app.domain.model.Batch mapDocToBatch(java.lang.String id, java.util.Map<java.lang.String, ? extends java.lang.Object> data) {
        return null;
    }
}