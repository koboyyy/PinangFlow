package com.pinangflow.app.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pinangflow.app.domain.model.Batch
import com.pinangflow.app.domain.model.BatchStatus
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.ui.components.BatchCard
import com.pinangflow.app.presentation.ui.components.TopAppBarGreen
import com.pinangflow.app.presentation.viewmodel.BatchViewModel

@Composable
fun ManajemenGudangScreen(
    modifier: Modifier = Modifier,
    viewModel: BatchViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Sedang Dijemur, 1 = Riwayat Selesai
    val firestoreBatches by viewModel.batches.collectAsState()

    var completedSortOption by remember { mutableStateOf("nomor_desc") } // "nomor_desc", "nomor_asc", "basah_desc", "susut_desc"

    // Separate active and completed lists
    val activeList = firestoreBatches.filter { it.status == BatchStatus.PROSES_PENGERINGAN }
        .sortedByDescending { it.nomorBatch }

    val completedList = firestoreBatches.filter { it.status == BatchStatus.SELESAI }
        .sortedWith { b1, b2 ->
            when (completedSortOption) {
                "nomor_asc" -> b1.nomorBatch.compareTo(b2.nomorBatch)
                "basah_desc" -> b2.beratBasah.compareTo(b1.beratBasah)
                "susut_desc" -> b2.persentaseSusut.compareTo(b1.persentaseSusut)
                else -> b2.nomorBatch.compareTo(b1.nomorBatch) // Default: Terbaru
            }
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // Header (Gear/Settings icon removed)
        TopAppBarGreen(
            title = "Manajemen Gudang",
            subtitle = "Monitoring Jemuran & Stok",
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
                        text = "Sedang Dijemur",
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
                        text = "Riwayat Selesai",
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
                SedangDijemurContent(
                    batches = activeList,
                    onBatchClick = onNavigateToDetail
                )
            }
            1 -> {
                RiwayatSelesaiContent(
                    batches = completedList,
                    selectedSort = completedSortOption,
                    onSortChange = { completedSortOption = it },
                    onBatchClick = onNavigateToDetail
                )
            }
        }
    }
}

@Composable
private fun SedangDijemurContent(
    batches: List<Batch>,
    onBatchClick: (String) -> Unit
) {
    if (batches.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tidak ada penjemuran saat ini.",
                color = TextSecondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(batches) { batch ->
                BatchCard(
                    batch = batch,
                    onClick = { onBatchClick(batch.id) }
                )
            }
        }
    }
}

@Composable
private fun RiwayatSelesaiContent(
    batches: List<Batch>,
    selectedSort: String,
    onSortChange: (String) -> Unit,
    onBatchClick: (String) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    val sortLabel = when (selectedSort) {
        "nomor_asc" -> "Batch Terlama"
        "basah_desc" -> "Berat Basah Terbesar"
        "susut_desc" -> "Susut Terbesar"
        else -> "Batch Terbaru"
    }

    if (batches.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Belum ada riwayat batch penjemuran selesai.",
                color = TextSecondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Interactive Filter & Sort Card
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { menuExpanded = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurface),
                        border = BorderStroke(1.dp, CardBorder.copy(alpha = 0.4f)),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = null,
                                    tint = PinangGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Urutan: $sortLabel",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Pilih urutan",
                                tint = TextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier.background(BackgroundWhite)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Batch Terbaru",
                                    color = if (selectedSort == "nomor_desc") PinangGreen else TextPrimary,
                                    fontWeight = if (selectedSort == "nomor_desc") FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            onClick = {
                                onSortChange("nomor_desc")
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Batch Terlama",
                                    color = if (selectedSort == "nomor_asc") PinangGreen else TextPrimary,
                                    fontWeight = if (selectedSort == "nomor_asc") FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            onClick = {
                                onSortChange("nomor_asc")
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Berat Basah Terbesar",
                                    color = if (selectedSort == "basah_desc") PinangGreen else TextPrimary,
                                    fontWeight = if (selectedSort == "basah_desc") FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            onClick = {
                                onSortChange("basah_desc")
                                menuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Persentase Susut Terbesar",
                                    color = if (selectedSort == "susut_desc") PinangGreen else TextPrimary,
                                    fontWeight = if (selectedSort == "susut_desc") FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            onClick = {
                                onSortChange("susut_desc")
                                menuExpanded = false
                            }
                        )
                    }
                }
            }

            items(batches) { batch ->
                BatchCard(
                    batch = batch,
                    onClick = { onBatchClick(batch.id) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
