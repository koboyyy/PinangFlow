package com.pinangflow.app.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\bf\u0018\u00002\u00020\u0001J,\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\b\u0010\tJ\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000bH&\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\r"}, d2 = {"Lcom/pinangflow/app/domain/repository/ExportRepository;", "", "addExportTransaction", "Lkotlin/Result;", "", "exportTransaction", "Lcom/pinangflow/app/domain/model/ExportTransaction;", "ownerWaNumber", "addExportTransaction-0E7RQCE", "(Lcom/pinangflow/app/domain/model/ExportTransaction;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExportTransactions", "Lkotlinx/coroutines/flow/Flow;", "", "app_debug"})
public abstract interface ExportRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.domain.model.ExportTransaction>> getExportTransactions();
}