package com.pinangflow.app.presentation.ui

object NavigationRoutes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val BERANDA = "beranda"
    const val DATA_PETANI = "data_petani"
    const val MANAJEMEN_GUDANG = "manajemen_gudang"
    const val LAPORAN = "laporan"
    const val PROFIL = "profil"
    const val PROFIL_PETANI = "profil_petani/{petaniId}"
    const val GUDANG_DETAIL = "gudang_detail/{batchId}"
    const val REGISTRASI_PETANI = "registrasi_petani"
    const val TRANSAKSI_ALL = "transaksi_all"
    const val TIMBANG_GRADING = "timbang_grading"
    const val PENJUALAN_EKSPOR = "penjualan_ekspor"

    fun profilPetani(petaniId: String) = "profil_petani/$petaniId"
    fun gudangDetail(batchId: String) = "gudang_detail/$batchId"
}
