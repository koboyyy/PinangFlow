package com.pinangflow.app.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H'J\u0014\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H'J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H'J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ&\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0012\u00a8\u0006\u0013"}, d2 = {"Lcom/pinangflow/app/data/local/StockDao;", "", "getAllStockEntries", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/pinangflow/app/data/local/StockEntryEntity;", "getDryingStockEntries", "getReadyStockEntriesAsc", "insertStockEntry", "", "entry", "(Lcom/pinangflow/app/data/local/StockEntryEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateWeightAndStatus", "entryId", "", "currentWeight", "", "newStatus", "(Ljava/lang/String;DLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface StockDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertStockEntry(@org.jetbrains.annotations.NotNull()
    com.pinangflow.app.data.local.StockEntryEntity entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM stock_entries")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.data.local.StockEntryEntity>> getAllStockEntries();
    
    @androidx.room.Query(value = "SELECT * FROM stock_entries WHERE status = 'DRYING'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.data.local.StockEntryEntity>> getDryingStockEntries();
    
    @androidx.room.Query(value = "SELECT * FROM stock_entries WHERE status = 'READY' ORDER BY entryTimestamp ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.pinangflow.app.data.local.StockEntryEntity>> getReadyStockEntriesAsc();
    
    @androidx.room.Query(value = "UPDATE stock_entries SET currentWeightKg = :currentWeight, status = :newStatus WHERE id = :entryId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateWeightAndStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String entryId, double currentWeight, @org.jetbrains.annotations.NotNull()
    java.lang.String newStatus, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}