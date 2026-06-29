package com.pinangflow.app.domain.model

data class ExportTransaction(
    val id: String = "",
    val tanggalKeluar: String = "",
    val namaEksportir: String = "",
    val beratJualKg: Double = 0.0,
    val hargaJualPerKg: Long = 0L,
    val totalPendapatan: Long = 0L,
    val catatan: String = ""
)
