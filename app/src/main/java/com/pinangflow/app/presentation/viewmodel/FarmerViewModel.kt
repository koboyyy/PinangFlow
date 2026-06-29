package com.pinangflow.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinangflow.app.domain.model.Farmer
import com.pinangflow.app.domain.model.FarmerTransaction
import com.pinangflow.app.domain.repository.FarmerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FarmerViewModel @Inject constructor(
    private val farmerRepository: FarmerRepository
) : ViewModel() {

    val farmers: StateFlow<List<Farmer>> = farmerRepository.getFarmers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getFarmer(farmerId: String): StateFlow<Farmer?> {
        return farmerRepository.getFarmer(farmerId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }

    fun getFarmerTransactions(farmerId: String): StateFlow<List<FarmerTransaction>> {
        return farmerRepository.getFarmerTransactions(farmerId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun addFarmer(
        nama: String,
        nomorTelepon: String,
        alamat: String,
        luasLahan: Double,
        jenisTanaman: String,
        onSuccess: () -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            val newFarmer = Farmer(
                id = UUID.randomUUID().toString(),
                nama = nama,
                nomorTelepon = nomorTelepon,
                alamat = alamat,
                luasLahan = luasLahan,
                jenisTanaman = jenisTanaman,
                statusAktif = true,
                totalDisetor = 0.0,
                sisaKasbon = 0.0
            )
            farmerRepository.addFarmer(newFarmer)
                .onSuccess { onSuccess() }
                .onFailure { onFailure(it) }
        }
    }

    fun addTransaction(
        farmerId: String,
        transaction: FarmerTransaction,
        onSuccess: () -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            farmerRepository.addTransaction(farmerId, transaction)
                .onSuccess { onSuccess() }
                .onFailure { onFailure(it) }
        }
    }
}
