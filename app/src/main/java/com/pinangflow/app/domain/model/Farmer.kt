package com.pinangflow.app.domain.model

data class Farmer(
    val id: String,
    val nama: String,
    val nomorTelepon: String,
    val alamat: String,
    val luasLahan: Double, // dalam hektar
    val jenisTanaman: String,
    val statusAktif: Boolean,
    val fotoUrl: String? = null,
    val totalDisetor: Double = 0.0, // dalam Kg
    val sisaKasbon: Double = 0.0 // dalam Rupiah
)
