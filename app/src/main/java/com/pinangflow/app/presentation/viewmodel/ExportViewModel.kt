package com.pinangflow.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinangflow.app.domain.model.ExportTransaction
import com.pinangflow.app.domain.repository.ExportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ExportViewModel @Inject constructor(
    private val exportRepository: ExportRepository
) : ViewModel() {

    val exportTransactions: StateFlow<List<ExportTransaction>> = exportRepository.getExportTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addExportTransaction(
        namaEksportir: String,
        beratJualKg: Double,
        hargaJualPerKg: Long,
        catatan: String,
        ownerWaNumber: String = "",
        onSuccess: (String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            val totalPendapatan = (beratJualKg * hargaJualPerKg).toLong()
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val tanggalStr = sdf.format(Date())

            val newTransaction = ExportTransaction(
                id = UUID.randomUUID().toString(),
                tanggalKeluar = tanggalStr,
                namaEksportir = namaEksportir,
                beratJualKg = beratJualKg,
                hargaJualPerKg = hargaJualPerKg,
                totalPendapatan = totalPendapatan,
                catatan = catatan
            )
            exportRepository.addExportTransaction(newTransaction, ownerWaNumber)
                .onSuccess { message -> onSuccess(message) }
                .onFailure { onFailure(it) }
        }
    }
}
