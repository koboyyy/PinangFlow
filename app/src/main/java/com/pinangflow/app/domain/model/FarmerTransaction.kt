package com.pinangflow.app.domain.model

enum class TransactionType {
    SETOR,
    KASBON_BARU,
    BAYAR
}

data class FarmerTransaction(
    val id: String,
    val jenis: TransactionType,
    val deskripsi: String,
    val tanggal: String,
    val jumlah: Long, // dalam Rupiah (positif = masuk, negatif = keluar)
    val label: String, // "Potong Kasbon", "Cash", "Hutang"
    val farmerName: String? = null,
    val timestamp: Long = 0L // millis since epoch, for time-based filtering
)
