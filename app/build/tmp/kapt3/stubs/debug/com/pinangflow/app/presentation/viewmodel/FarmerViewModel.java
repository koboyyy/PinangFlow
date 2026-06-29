package com.pinangflow.app.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004JT\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000e2\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u00152\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\f0\u0017J<\u0010\u0019\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001c2\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u00152\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\f0\u0017J\u0016\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00062\u0006\u0010\u001a\u001a\u00020\u000eJ\u001a\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u00070\u00062\u0006\u0010\u001a\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u001f"}, d2 = {"Lcom/pinangflow/app/presentation/viewmodel/FarmerViewModel;", "Landroidx/lifecycle/ViewModel;", "farmerRepository", "Lcom/pinangflow/app/domain/repository/FarmerRepository;", "(Lcom/pinangflow/app/domain/repository/FarmerRepository;)V", "farmers", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/pinangflow/app/domain/model/Farmer;", "getFarmers", "()Lkotlinx/coroutines/flow/StateFlow;", "addFarmer", "", "nama", "", "nomorTelepon", "alamat", "luasLahan", "", "jenisTanaman", "onSuccess", "Lkotlin/Function0;", "onFailure", "Lkotlin/Function1;", "", "addTransaction", "farmerId", "transaction", "Lcom/pinangflow/app/domain/model/FarmerTransaction;", "getFarmer", "getFarmerTransactions", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class FarmerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.domain.repository.FarmerRepository farmerRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pinangflow.app.domain.model.Farmer>> farmers = null;
    
    @javax.inject.Inject()
    public FarmerViewModel(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.repository.FarmerRepository farmerRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pinangflow.app.domain.model.Farmer>> getFarmers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pinangflow.app.domain.model.Farmer> getFarmer(@org.jetbrains.annotations.NotNull()
    java.lang.String farmerId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.pinangflow.app.domain.model.FarmerTransaction>> getFarmerTransactions(@org.jetbrains.annotations.NotNull()
    java.lang.String farmerId) {
        return null;
    }
    
    public final void addFarmer(@org.jetbrains.annotations.NotNull()
    java.lang.String nama, @org.jetbrains.annotations.NotNull()
    java.lang.String nomorTelepon, @org.jetbrains.annotations.NotNull()
    java.lang.String alamat, double luasLahan, @org.jetbrains.annotations.NotNull()
    java.lang.String jenisTanaman, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onFailure) {
    }
    
    public final void addTransaction(@org.jetbrains.annotations.NotNull()
    java.lang.String farmerId, @org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.model.FarmerTransaction transaction, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onFailure) {
    }
}