package com.pinangflow.app.domain.model

data class DailyPrice(
    val tanggal: String,
    val hargaGradeA: Long, // dalam Rupiah per kg
    val hargaGradeB: Long  // dalam Rupiah per kg
)
