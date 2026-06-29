package com.pinangflow.app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.ui.components.*
import com.pinangflow.app.domain.model.UnifiedTransaction
import com.pinangflow.app.presentation.viewmodel.BatchViewModel
import com.pinangflow.app.presentation.viewmodel.ExportViewModel

@Composable
fun SemuaTransaksiScreen(
    modifier: Modifier = Modifier,
    viewModel: BatchViewModel = hiltViewModel(),
    exportViewModel: ExportViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val farmerTransactions by viewModel.transactions.collectAsState()
    val exportTransactions by exportViewModel.exportTransactions.collectAsState()

    val combinedTransactions = androidx.compose.runtime.remember(farmerTransactions, exportTransactions) {
        val fTx = farmerTransactions.map {
            UnifiedTransaction(
                id = it.id,
                title = it.farmerName ?: "Petani",
                detail = it.deskripsi,
                jumlah = it.jumlah,
                tanggal = it.tanggal,
                timestamp = it.timestamp,
                isIncome = it.jumlah >= 0
            )
        }
        val eTx = exportTransactions.map {
            val sdfInput = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
            val ts = try { sdfInput.parse(it.tanggalKeluar)?.time ?: 0L } catch (e: Exception) { 0L }
            UnifiedTransaction(
                id = it.id,
                title = it.namaEksportir,
                detail = "Penjualan Ekspor",
                jumlah = it.totalPendapatan,
                tanggal = it.tanggalKeluar,
                timestamp = ts,
                isIncome = true
            )
        }
        (fTx + eTx).sortedByDescending { it.timestamp }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // Header
        TopAppBarGreen(
            title = "Semua Transaksi",
            subtitle = "Daftar transaksi keluar & masuk",
            showBackButton = true,
            onBackClick = onBackClick
        )

        if (combinedTransactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada riwayat transaksi ditemukan.",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            ) {
                itemsIndexed(combinedTransactions) { index, tx ->
                    val prefix = if (tx.isIncome) "+ " else "- "
                    val amountText = "${prefix}Rp ${String.format("%,d", kotlin.math.abs(tx.jumlah)).replace(',', '.')}"

                    TransactionItem(
                        avatarInitials = tx.title.take(2).uppercase(),
                        avatarColor = if (tx.isIncome) PinangGreen else PinangAmber,
                        nama = tx.title,
                        detail = tx.detail,
                        jumlah = amountText,
                        waktu = tx.tanggal,
                        isIncome = tx.isIncome,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (index < combinedTransactions.size - 1) {
                        HorizontalDivider(color = DividerColor, thickness = 1.dp)
                    }
                }
            }
        }
    }
}
