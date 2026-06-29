package com.pinangflow.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinangflow.app.domain.model.Batch
import com.pinangflow.app.domain.model.BatchStatus
import com.pinangflow.app.domain.repository.BatchRepository
import com.pinangflow.app.domain.model.FarmerTransaction
import com.pinangflow.app.domain.repository.FarmerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BatchViewModel @Inject constructor(
    private val batchRepository: BatchRepository,
    private val farmerRepository: FarmerRepository
) : ViewModel() {

    val transactions: StateFlow<List<FarmerTransaction>> = farmerRepository.getGlobalTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val batches: StateFlow<List<Batch>> = batchRepository.getBatches()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val stats: StateFlow<Map<String, Any>> = batchRepository.getLaporanStats()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyMap()
        )

    fun getBatch(batchId: String): StateFlow<Batch?> {
        return batchRepository.getBatch(batchId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }

    fun updateBatchDryWeight(
        batchId: String,
        dryWeight: Double,
        onSuccess: () -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            batchRepository.updateBatchDryWeight(batchId, dryWeight)
                .onSuccess { onSuccess() }
                .onFailure { onFailure(it) }
        }
    }

    fun addBatch(
        nomorBatch: Int,
        beratBasah: Double,
        areaJemur: String,
        suhu: String,
        onSuccess: () -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            val newBatch = Batch(
                id = UUID.randomUUID().toString(),
                nomorBatch = nomorBatch,
                tanggalMasuk = "Hari Ini",
                beratBasah = beratBasah,
                status = BatchStatus.PROSES_PENGERINGAN,
                areaJemur = areaJemur,
                suhu = suhu
            )
            batchRepository.addBatch(newBatch)
                .onSuccess { onSuccess() }
                .onFailure { onFailure(it) }
        }
    }

    fun updateDailyPrices(
        hargaGradeA: Long,
        hargaGradeB: Long,
        hargaGradeC: Long,
        onSuccess: () -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            batchRepository.updateDailyPrices(hargaGradeA, hargaGradeB, hargaGradeC)
                .onSuccess { onSuccess() }
                .onFailure { onFailure(it) }
        }
    }
}
