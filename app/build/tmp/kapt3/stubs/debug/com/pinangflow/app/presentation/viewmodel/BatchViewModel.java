package com.pinangflow.app.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0010\t\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006JL\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000f2\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u001e2\u0014\b\u0002\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00160 J\u0016\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\b2\u0006\u0010#\u001a\u00020\u000fJ<\u0010$\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020\u001a2\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u001e2\u0014\b\u0002\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00160 JD\u0010&\u001a\u00020\u00162\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020(2\u0006\u0010*\u001a\u00020(2\u000e\b\u0002\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u001e2\u0014\b\u0002\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00160 R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\f\u00a8\u0006+"}, d2 = {"Lcom/pinangflow/app/presentation/viewmodel/BatchViewModel;", "Landroidx/lifecycle/ViewModel;", "batchRepository", "Lcom/pinangflow/app/domain/repository/BatchRepository;", "farmerRepository", "Lcom/pinangflow/app/domain/repository/FarmerRepository;", "(Lcom/pinangflow/app/domain/repository/BatchRepository;Lcom/pinangflow/app/domain/repository/FarmerRepository;)V", "batches", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/pinangflow/app/domain/model/Batch;", "getBatches", "()Lkotlinx/coroutines/flow/StateFlow;", "stats", "", "", "", "getStats", "transactions", "Lcom/pinangflow/app/domain/model/FarmerTransaction;", "getTransactions", "addBatch", "", "nomorBatch", "", "beratBasah", "", "areaJemur", "suhu", "onSuccess", "Lkotlin/Function0;", "onFailure", "Lkotlin/Function1;", "", "getBatch", "batchId", "updateBatchDryWeight", "dryWeight", "updateDailyPrices", "hargaGradeA", "", "hargaGradeB", "hargaGradeC", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class BatchViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.domain.repository.BatchRepository batchRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.domain.repository.FarmerRepository farmerRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pinangflow.app.domain.model.FarmerTransaction>> transactions = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pinangflow.app.domain.model.Batch>> batches = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, java.lang.Object>> stats = null;
    
    @javax.inject.Inject()
    public BatchViewModel(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.repository.BatchRepository batchRepository, @org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.repository.FarmerRepository farmerRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pinangflow.app.domain.model.FarmerTransaction>> getTransactions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pinangflow.app.domain.model.Batch>> getBatches() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, java.lang.Object>> getStats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pinangflow.app.domain.model.Batch> getBatch(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId) {
        return null;
    }
    
    public final void updateBatchDryWeight(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, double dryWeight, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onFailure) {
    }
    
    public final void addBatch(int nomorBatch, double beratBasah, @org.jetbrains.annotations.NotNull()
    java.lang.String areaJemur, @org.jetbrains.annotations.NotNull()
    java.lang.String suhu, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onFailure) {
    }
    
    public final void updateDailyPrices(long hargaGradeA, long hargaGradeB, long hargaGradeC, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onFailure) {
    }
}