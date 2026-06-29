package com.pinangflow.app.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pinangflow.app.domain.model.ExportTransaction
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.ui.components.TopAppBarGreen
import com.pinangflow.app.presentation.viewmodel.ExportViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenjualanEksporScreen(
    onBackClick: () -> Unit,
    viewModel: ExportViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Riwayat, 1 = Form
    val transactions by viewModel.exportTransactions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        TopAppBarGreen(
            title = "Penjualan Ekspor",
            subtitle = "Kelola Pengiriman & Riwayat",
            showBackButton = true,
            onBackClick = onBackClick
        )

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = BackgroundWhite,
            contentColor = PinangGreen,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = PinangGreen,
                    height = 3.dp
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        text = "Riwayat Ekspor",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                },
                selectedContentColor = PinangGreen,
                unselectedContentColor = TextSecondary
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        text = "Form Penjualan",
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                },
                selectedContentColor = PinangGreen,
                unselectedContentColor = TextSecondary
            )
        }

        when (selectedTab) {
            0 -> {
                RiwayatEksporContent(transactions = transactions)
            }
            1 -> {
                FormPenjualanContent(
                    viewModel = viewModel,
                    onSuccess = { selectedTab = 0 } // Pindah kembali ke tab riwayat saat sukses
                )
            }
        }
    }
}

@Composable
fun RiwayatEksporContent(transactions: List<ExportTransaction>) {
    if (transactions.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Belum ada riwayat penjualan ekspor.", color = TextSecondary)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(transactions.sortedByDescending { it.tanggalKeluar }) { tx ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(IconBgGreen, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.LocalShipping, contentDescription = null, tint = PinangGreen, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(text = tx.namaEksportir, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                                    Text(text = tx.tanggalKeluar, fontSize = 12.sp, color = TextSecondary)
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = DividerColor)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("Berat Terjual", fontSize = 12.sp, color = TextSecondary)
                                Text("${tx.beratJualKg} Kg", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Harga / Kg", fontSize = 12.sp, color = TextSecondary)
                                Text("Rp ${String.format("%,d", tx.hargaJualPerKg).replace(',', '.')}", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(IconBgGreen, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Total Pendapatan", fontWeight = FontWeight.Medium, color = TextSecondary, fontSize = 13.sp)
                                Text("Rp ${String.format("%,d", tx.totalPendapatan).replace(',', '.')}", fontWeight = FontWeight.Bold, color = PinangGreen, fontSize = 16.sp)
                            }
                        }

                        if (tx.catatan.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(imageVector = Icons.Default.Receipt, contentDescription = null, tint = TextTertiary, modifier = Modifier.size(14.dp).padding(top = 2.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Catatan: ${tx.catatan}", fontSize = 12.sp, color = TextTertiary, lineHeight = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FormPenjualanContent(
    viewModel: ExportViewModel,
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var namaEksportir by remember { mutableStateOf("") }
    var beratJual by remember { mutableStateOf("") }
    var hargaJual by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }

    var isSubmitting by remember { mutableStateOf(false) }

    val beratVal = beratJual.toDoubleOrNull() ?: 0.0
    val hargaVal = hargaJual.toLongOrNull() ?: 0L
    val totalPendapatan = (beratVal * hargaVal).toLong()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Form Pengeluaran Stok",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        OutlinedTextField(
            value = namaEksportir,
            onValueChange = { namaEksportir = it },
            label = { Text("Nama Eksportir / Pembeli") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PinangGreen,
                focusedLabelColor = PinangGreen
            ),
            singleLine = true
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = beratJual,
                onValueChange = { beratJual = it },
                label = { Text("Berat Terjual (Kg)") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PinangGreen,
                    focusedLabelColor = PinangGreen
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = hargaJual,
                onValueChange = { hargaJual = it },
                label = { Text("Harga / Kg (Rp)") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PinangGreen,
                    focusedLabelColor = PinangGreen
                ),
                singleLine = true
            )
        }

        OutlinedTextField(
            value = catatan,
            onValueChange = { catatan = it },
            label = { Text("Catatan / No. Surat Jalan (Opsional)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PinangGreen,
                focusedLabelColor = PinangGreen
            ),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Kalkulasi Pendapatan Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = IconBgGreen, shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Total Pendapatan (Estimasi)",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp ${String.format("%,d", totalPendapatan).replace(',', '.')}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinangGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (namaEksportir.isBlank() || beratVal <= 0 || hargaVal <= 0) {
                    Toast.makeText(context, "Harap lengkapi nama eksportir, berat, dan harga!", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                isSubmitting = true
                viewModel.addExportTransaction(
                    namaEksportir = namaEksportir.trim(),
                    beratJualKg = beratVal,
                    hargaJualPerKg = hargaVal,
                    catatan = catatan.trim(),
                    onSuccess = { message ->
                        isSubmitting = false
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        // Reset form
                        namaEksportir = ""
                        beratJual = ""
                        hargaJual = ""
                        catatan = ""
                        // Pindah tab
                        onSuccess()
                    },
                    onFailure = { error ->
                        isSubmitting = false
                        Toast.makeText(context, "Gagal: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PinangGreen),
            enabled = !isSubmitting
        ) {
            if (isSubmitting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "SIMPAN TRANSAKSI",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
