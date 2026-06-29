package com.pinangflow.app.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J0\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0018\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\r0\u0012J0\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\u00172\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\r0\u0019R\u0019\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/pinangflow/app/presentation/viewmodel/ProfileViewModel;", "Landroidx/lifecycle/ViewModel;", "profileRepository", "Lcom/pinangflow/app/domain/repository/ProfileRepository;", "whatsAppHelper", "Lcom/pinangflow/app/data/repository/WhatsAppHelper;", "(Lcom/pinangflow/app/domain/repository/ProfileRepository;Lcom/pinangflow/app/data/repository/WhatsAppHelper;)V", "profile", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/pinangflow/app/domain/model/PengepulProfile;", "getProfile", "()Lkotlinx/coroutines/flow/StateFlow;", "testWhatsAppConnection", "", "nomorWa", "", "token", "onResult", "Lkotlin/Function2;", "", "updateProfile", "updatedProfile", "onSuccess", "Lkotlin/Function0;", "onFailure", "Lkotlin/Function1;", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ProfileViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.domain.repository.ProfileRepository profileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.data.repository.WhatsAppHelper whatsAppHelper = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pinangflow.app.domain.model.PengepulProfile> profile = null;
    
    @javax.inject.Inject()
    public ProfileViewModel(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.repository.ProfileRepository profileRepository, @org.jetbrains.annotations.NotNull()
    com.pinangflow.app.data.repository.WhatsAppHelper whatsAppHelper) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pinangflow.app.domain.model.PengepulProfile> getProfile() {
        return null;
    }
    
    public final void updateProfile(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.model.PengepulProfile updatedProfile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onFailure) {
    }
    
    public final void testWhatsAppConnection(@org.jetbrains.annotations.NotNull()
    java.lang.String nomorWa, @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> onResult) {
    }
}