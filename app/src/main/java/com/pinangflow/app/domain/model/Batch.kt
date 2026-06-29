package com.pinangflow.app.domain.model

enum class BatchStatus {
    PROSES_PENGERINGAN,
    SELESAI
}

data class Batch(
    val id: String,
    val nomorBatch: Int,
    val tanggalMasuk: String,
    val tanggalSelesai: String? = null,
    val beratBasah: Double, // dalam Kg
    val beratKering: Double = 0.0, // dalam Kg
    val status: BatchStatus,
    val areaJemur: String = "",
    val suhu: String = ""
) {
    val persentaseSusut: Double
        get() = if (beratBasah > 0 && beratKering > 0) {
            ((beratBasah - beratKering) / beratBasah) * 100
        } else 0.0

    val kehilanganBerat: Double
        get() = if (beratKering > 0) beratBasah - beratKering else 0.0
}
