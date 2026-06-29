package com.pinangflow.app.domain.usecase;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086B\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\f"}, d2 = {"Lcom/pinangflow/app/domain/usecase/UpdateShrinkageUseCase;", "", "stockRepository", "Lcom/pinangflow/app/domain/repository/StockRepository;", "calculateShrinkageUseCase", "Lcom/pinangflow/app/domain/usecase/CalculateShrinkageUseCase;", "(Lcom/pinangflow/app/domain/repository/StockRepository;Lcom/pinangflow/app/domain/usecase/CalculateShrinkageUseCase;)V", "invoke", "Lkotlin/Result;", "", "invoke-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class UpdateShrinkageUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.domain.repository.StockRepository stockRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.domain.usecase.CalculateShrinkageUseCase calculateShrinkageUseCase = null;
    
    @javax.inject.Inject()
    public UpdateShrinkageUseCase(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.repository.StockRepository stockRepository, @org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.usecase.CalculateShrinkageUseCase calculateShrinkageUseCase) {
        super();
    }
}