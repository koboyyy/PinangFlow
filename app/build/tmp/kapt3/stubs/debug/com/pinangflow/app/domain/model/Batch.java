package com.pinangflow.app.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\tH\u00c6\u0003J\t\u0010&\u001a\u00020\tH\u00c6\u0003J\t\u0010'\u001a\u00020\fH\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003Je\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020\u0005H\u00d6\u0001J\t\u0010/\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u0016\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0011R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0011\u00a8\u00060"}, d2 = {"Lcom/pinangflow/app/domain/model/Batch;", "", "id", "", "nomorBatch", "", "tanggalMasuk", "tanggalSelesai", "beratBasah", "", "beratKering", "status", "Lcom/pinangflow/app/domain/model/BatchStatus;", "areaJemur", "suhu", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;DDLcom/pinangflow/app/domain/model/BatchStatus;Ljava/lang/String;Ljava/lang/String;)V", "getAreaJemur", "()Ljava/lang/String;", "getBeratBasah", "()D", "getBeratKering", "getId", "kehilanganBerat", "getKehilanganBerat", "getNomorBatch", "()I", "persentaseSusut", "getPersentaseSusut", "getStatus", "()Lcom/pinangflow/app/domain/model/BatchStatus;", "getSuhu", "getTanggalMasuk", "getTanggalSelesai", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class Batch {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    private final int nomorBatch = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String tanggalMasuk = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String tanggalSelesai = null;
    private final double beratBasah = 0.0;
    private final double beratKering = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final com.pinangflow.app.domain.model.BatchStatus status = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String areaJemur = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String suhu = null;
    
    public Batch(@org.jetbrains.annotations.NotNull()
    java.lang.String id, int nomorBatch, @org.jetbrains.annotations.NotNull()
    java.lang.String tanggalMasuk, @org.jetbrains.annotations.Nullable()
    java.lang.String tanggalSelesai, double beratBasah, double beratKering, @org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.model.BatchStatus status, @org.jetbrains.annotations.NotNull()
    java.lang.String areaJemur, @org.jetbrains.annotations.NotNull()
    java.lang.String suhu) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    public final int getNomorBatch() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTanggalMasuk() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTanggalSelesai() {
        return null;
    }
    
    public final double getBeratBasah() {
        return 0.0;
    }
    
    public final double getBeratKering() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pinangflow.app.domain.model.BatchStatus getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAreaJemur() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSuhu() {
        return null;
    }
    
    public final double getPersentaseSusut() {
        return 0.0;
    }
    
    public final double getKehilanganBerat() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final double component6() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pinangflow.app.domain.model.BatchStatus component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pinangflow.app.domain.model.Batch copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, int nomorBatch, @org.jetbrains.annotations.NotNull()
    java.lang.String tanggalMasuk, @org.jetbrains.annotations.Nullable()
    java.lang.String tanggalSelesai, double beratBasah, double beratKering, @org.jetbrains.annotations.NotNull()
    com.pinangflow.app.domain.model.BatchStatus status, @org.jetbrains.annotations.NotNull()
    java.lang.String areaJemur, @org.jetbrains.annotations.NotNull()
    java.lang.String suhu) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}