package com.pinangflow.app.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004JX\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u000e2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\f0\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\f0\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0019"}, d2 = {"Lcom/pinangflow/app/presentation/viewmodel/ExportViewModel;", "Landroidx/lifecycle/ViewModel;", "exportRepository", "Lcom/pinangflow/app/domain/repository/ExportRepository;", "(Lcom/pinangflow/app/domain/repository/ExportRepository;)V", "exportTransactions", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/pinangflow/app/domain/model/ExportTransaction;", "getExportTransactions", "()Lkotlinx/coroutines/flow/StateFlow;", "addExportTransaction", "", "namaEksportir", "", "beratJualKg", "", "hargaJualPerKg", "", "catatan", "ownerWaNumber", "onSuccess", "Lkotlin/Function1;", "onFailure", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ExportViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.domain.repository.ExportRepository exportRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pinangflow.app.domain.model.ExportTransaction>> exportTransactions = null;
    
    @javax.inject.Inject()
    public ExportViewModel(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.repository.ExportRepository exportRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pinangflow.app.domain.model.ExportTransaction>> getExportTransactions() {
        return null;
    }
    
    public final void addExportTransaction(@org.jetbrains.annotations.NotNull()
    java.lang.String namaEksportir, double beratJualKg, long hargaJualPerKg, @org.jetbrains.annotations.NotNull()
    java.lang.String catatan, @org.jetbrains.annotations.NotNull()
    java.lang.String ownerWaNumber, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onFailure) {
    }
}