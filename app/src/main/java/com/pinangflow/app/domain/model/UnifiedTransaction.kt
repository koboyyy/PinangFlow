package com.pinangflow.app.domain.model

data class UnifiedTransaction(
    val id: String,
    val title: String, // Nama Petani / Nama Eksportir
    val detail: String, // "Setor Pinang", "Potong Kasbon", "Penjualan Ekspor", dll
    val jumlah: Long, // Nominal uang, positif = masuk (ekspor/setor), negatif = keluar (bayar/kasbon)
    val tanggal: String,
    val timestamp: Long, // Untuk sorting
    val isIncome: Boolean // Untuk styling warna di UI (+ hijau, - merah/abu)
)
