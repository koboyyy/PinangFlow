package com.pinangflow.app.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.ui.components.FarmerCard
import com.pinangflow.app.presentation.ui.components.TopAppBarGreen
import com.pinangflow.app.presentation.viewmodel.FarmerViewModel

@Composable
fun DataPetaniScreen(
    modifier: Modifier = Modifier,
    viewModel: FarmerViewModel = hiltViewModel(),
    onNavigateToProfile: (String) -> Unit = {},
    onNavigateToRegistration: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    val firestoreFarmers by viewModel.farmers.collectAsState()

    var filterMenuExpanded by remember { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf("nama") } // "nama", "kasbon", "setoran"
    var selectedStatusFilter by remember { mutableStateOf("semua") } // "semua", "aktif", "nonaktif"

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // Header
        TopAppBarGreen(
            title = "Data Petani",
            trailingIcon = Icons.Default.PersonAdd,
            onTrailingClick = onNavigateToRegistration
        )

        // Search + Filter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        text = "Cari nama atau ID petani...",
                        fontSize = 14.sp,
                        color = TextTertiary
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Cari",
                        tint = TextSecondary
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = CardBorder,
                    focusedBorderColor = PinangGreen
                ),
                singleLine = true
            )

            Box {
                IconButton(
                    onClick = { filterMenuExpanded = true },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filter & Urutkan",
                        tint = TextPrimary
                    )
                }

                DropdownMenu(
                    expanded = filterMenuExpanded,
                    onDismissRequest = { filterMenuExpanded = false },
                    modifier = Modifier.background(BackgroundWhite)
                ) {
                    // Status Filters
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Semua Mitra",
                                fontWeight = if (selectedStatusFilter == "semua") FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedStatusFilter == "semua") PinangGreen else TextPrimary
                            )
                        },
                        onClick = { selectedStatusFilter = "semua"; filterMenuExpanded = false }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Mitra Aktif",
                                fontWeight = if (selectedStatusFilter == "aktif") FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedStatusFilter == "aktif") PinangGreen else TextPrimary
                            )
                        },
                        onClick = { selectedStatusFilter = "aktif"; filterMenuExpanded = false }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Mitra Non-aktif",
                                fontWeight = if (selectedStatusFilter == "nonaktif") FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedStatusFilter == "nonaktif") PinangGreen else TextPrimary
                            )
                        },
                        onClick = { selectedStatusFilter = "nonaktif"; filterMenuExpanded = false }
                    )

                    HorizontalDivider(color = DividerColor, modifier = Modifier.padding(vertical = 4.dp))

                    // Sorting
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Urutkan: Nama (A-Z)",
                                fontWeight = if (selectedSortOption == "nama") FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedSortOption == "nama") PinangGreen else TextPrimary
                            )
                        },
                        onClick = { selectedSortOption = "nama"; filterMenuExpanded = false }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Urutkan: Kasbon Terbanyak",
                                fontWeight = if (selectedSortOption == "kasbon") FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedSortOption == "kasbon") PinangGreen else TextPrimary
                            )
                        },
                        onClick = { selectedSortOption = "kasbon"; filterMenuExpanded = false }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Urutkan: Setoran Terbanyak",
                                fontWeight = if (selectedSortOption == "setoran") FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedSortOption == "setoran") PinangGreen else TextPrimary
                            )
                        },
                        onClick = { selectedSortOption = "setoran"; filterMenuExpanded = false }
                    )
                }
            }
        }

        // Filter and Sort execution
        val filteredAndSortedFarmers = firestoreFarmers.filter { farmer ->
            (searchQuery.isBlank() || farmer.nama.contains(searchQuery, ignoreCase = true) || farmer.id.contains(searchQuery, ignoreCase = true)) &&
            (selectedStatusFilter == "semua" ||
             (selectedStatusFilter == "aktif" && farmer.statusAktif) ||
             (selectedStatusFilter == "nonaktif" && !farmer.statusAktif))
        }.sortedWith { f1, f2 ->
            when (selectedSortOption) {
                "kasbon" -> f2.sisaKasbon.compareTo(f1.sisaKasbon) // Descending
                "setoran" -> f2.totalDisetor.compareTo(f1.totalDisetor) // Descending
                else -> f1.nama.compareTo(f2.nama, ignoreCase = true) // A-Z Ascending
            }
        }

        // Farmer List
        if (firestoreFarmers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Belum ada data petani terdaftar.\nTekan tombol + di pojok kanan atas untuk menambah petani.",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }
        } else if (filteredAndSortedFarmers.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada petani yang cocok dengan pencarian dan filter aktif.",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredAndSortedFarmers) { farmer ->
                    FarmerCard(
                        farmer = farmer,
                        onViewClick = { onNavigateToProfile(farmer.id) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
