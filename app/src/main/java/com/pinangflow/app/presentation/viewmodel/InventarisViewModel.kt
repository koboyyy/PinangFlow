package com.pinangflow.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinangflow.app.domain.model.StockEntry
import com.pinangflow.app.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class InventarisUiState(
    val dryingStocks: List<StockEntry> = emptyList(),
    val readyStocks: List<StockEntry> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class InventarisViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    val uiState: StateFlow<InventarisUiState> = stockRepository.getAllStockEntries()
        .map { allStocks ->
            InventarisUiState(
                dryingStocks = allStocks.filter { it.status.name == "DRYING" },
                readyStocks = allStocks.filter { it.status.name == "READY" }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = InventarisUiState(isLoading = true)
        )
}
