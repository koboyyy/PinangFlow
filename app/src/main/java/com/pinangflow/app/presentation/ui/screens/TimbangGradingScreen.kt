package com.pinangflow.app.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.*
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
import com.pinangflow.app.domain.model.Batch
import com.pinangflow.app.domain.model.BatchStatus
import com.pinangflow.app.domain.model.Farmer
import com.pinangflow.app.domain.model.TransactionType
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.ui.components.TopAppBarGreen
import com.pinangflow.app.presentation.viewmodel.BatchViewModel
import com.pinangflow.app.presentation.viewmodel.FarmerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimbangGradingScreen(
    modifier: Modifier = Modifier,
    batchViewModel: BatchViewModel = hiltViewModel(),
    farmerViewModel: FarmerViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Farmers & stats state
    val farmers by farmerViewModel.farmers.collectAsState()
    val stats by batchViewModel.stats.collectAsState()
    val batches by batchViewModel.batches.collectAsState()

    // Form inputs
    var selectedFarmer by remember { mutableStateOf<Farmer?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    var beratBasahInput by remember { mutableStateOf("") }
    var selectedGrade by remember { mutableStateOf("Grade A") } // Grade A, Grade B, Grade C
    var areaJemurInput by remember { mutableStateOf("") }
    var suhuInput by remember { mutableStateOf("32°C") }

    var isSaving by remember { mutableStateOf(false) }

    // Fetch daily prices
    val hargaGradeAVal = stats["hargaGradeA"] ?: 12000L
    val hargaGradeBVal = stats["hargaGradeB"] ?: 9500L
    val hargaGradeCVal = stats["hargaGradeC"] ?: 7000L
    val hargaGradeA = (hargaGradeAVal as? Number)?.toLong() ?: 12000L
    val hargaGradeB = (hargaGradeBVal as? Number)?.toLong() ?: 9500L
    val hargaGradeC = (hargaGradeCVal as? Number)?.toLong() ?: 7000L

    val activePrice = when (selectedGrade) {
        "Grade A" -> hargaGradeA
        "Grade B" -> hargaGradeB
        else -> hargaGradeC
    }

    // Calculations
    val beratBasah = beratBasahInput.toDoubleOrNull() ?: 0.0
    val totalValuation = beratBasah * activePrice

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // Top App Bar
        TopAppBarGreen(
            title = "Timbang & Grading Baru",
            subtitle = "Catat transaksi pembelian & penjemuran",
            showBackButton = true,
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // === STEP 1: PILIH MITRA / PETANI ===
            Column {
                Text(
                    text = "Pilih Mitra / Petani",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                ExposedDropdownMenuBox(
                    expanded = dropdownExpanded,
                    onExpandedChange = { dropdownExpanded = !dropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedFarmer?.nama ?: "Pilih Petani...",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = CardBorder,
                            focusedBorderColor = PinangGreen
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        modifier = Modifier.background(BackgroundWhite)
                    ) {
                        if (farmers.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("Belum ada data petani") },
                                onClick = { dropdownExpanded = false }
                            )
                        } else {
                            farmers.forEach { farmer ->
                                DropdownMenuItem(
                                    text = { Text(farmer.nama) },
                                    onClick = {
                                        selectedFarmer = farmer
                                        dropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // === STEP 2: INPUT BERAT BASAH ===
            Column {
                Text(
                    text = "Berat Basah Hasil Timbang (Kg)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = beratBasahInput,
                    onValueChange = { beratBasahInput = it },
                    placeholder = { Text("0.0") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    suffix = { Text("Kg") },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = CardBorder,
                        focusedBorderColor = PinangGreen
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // === STEP 3: PILIH GRADE ===
            Column {
                Text(
                    text = "Grade Pinang",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Grade A
                    val isASelected = selectedGrade == "Grade A"
                    Button(
                        onClick = { selectedGrade = "Grade A" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isASelected) PinangGreen else Color(0xFFF5F5F5),
                            contentColor = if (isASelected) Color.White else TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = if (isASelected) null else BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Grade A", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Rp ${String.format("%,d", hargaGradeA).replace(',', '.')}/kg", fontSize = 10.sp)
                        }
                    }

                    // Grade B
                    val isBSelected = selectedGrade == "Grade B"
                    Button(
                        onClick = { selectedGrade = "Grade B" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isBSelected) PinangGreen else Color(0xFFF5F5F5),
                            contentColor = if (isBSelected) Color.White else TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = if (isBSelected) null else BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Grade B", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Rp ${String.format("%,d", hargaGradeB).replace(',', '.')}/kg", fontSize = 10.sp)
                        }
                    }

                    // Grade C
                    val isCSelected = selectedGrade == "Grade C"
                    Button(
                        onClick = { selectedGrade = "Grade C" },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCSelected) PinangGreen else Color(0xFFF5F5F5),
                            contentColor = if (isCSelected) Color.White else TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = if (isCSelected) null else BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Grade C", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text("Rp ${String.format("%,d", hargaGradeC).replace(',', '.')}/kg", fontSize = 10.sp)
                        }
                    }
                }
            }

            // === PENGERINGAN / GUDANG DETAILS ===
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface),
                border = BorderStroke(1.dp, CardBorder.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "DETAIL GUDANG / PENJEMURAN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        letterSpacing = 1.sp
                    )

                    // Area Jemur
                    Column {
                        Text(
                            text = "Area Lokasi Jemur",
                            fontSize = 13.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = areaJemurInput,
                            onValueChange = { areaJemurInput = it },
                            placeholder = { Text("Contoh: Area Jemur A-3") },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = CardBorder,
                                focusedBorderColor = PinangGreen
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Suhu
                    Column {
                        Text(
                            text = "Suhu Lingkungan",
                            fontSize = 13.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = suhuInput,
                            onValueChange = { suhuInput = it },
                            placeholder = { Text("32°C") },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = CardBorder,
                                focusedBorderColor = PinangGreen
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // === ESTIMASI VALUASI TRANSAKSI ===
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = IconBgGreen.copy(alpha = 0.6f)),
                border = BorderStroke(1.dp, PinangGreen.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            tint = PinangGreen,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Estimasi Nilai Transaksi",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PinangGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Total Pembayaran", fontSize = 12.sp, color = TextSecondary)
                            Text(
                                text = "Rp ${String.format("%,.0f", totalValuation).replace(',', '.')}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(PinangGreenSurface, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${String.format("%,.1f", beratBasah)} Kg x Rp ${String.format("%,d", activePrice).replace(',', '.')}",
                                fontSize = 11.sp,
                                color = PinangGreen,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // === BOTTOM BUTTON ===
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp,
            color = BackgroundWhite
        ) {
            Button(
                onClick = {
                    if (selectedFarmer == null) {
                        Toast.makeText(context, "Silakan pilih petani terlebih dahulu!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (beratBasah <= 0) {
                        Toast.makeText(context, "Berat basah harus lebih besar dari 0!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (areaJemurInput.isBlank()) {
                        Toast.makeText(context, "Area jemur wajib diisi!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isSaving = true

                    // Compute dynamic batch number
                    val nextBatchNum = if (batches.isNotEmpty()) {
                        (batches.maxOf { it.nomorBatch }) + 1
                    } else 105

                    // 1. Add Batch penjemuran
                    batchViewModel.addBatch(
                        nomorBatch = nextBatchNum,
                        beratBasah = beratBasah,
                        areaJemur = areaJemurInput,
                        suhu = suhuInput,
                        onSuccess = {
                            // 2. Add Farmer transaction log
                            val transaction = com.pinangflow.app.domain.model.FarmerTransaction(
                                id = java.util.UUID.randomUUID().toString(),
                                jenis = com.pinangflow.app.domain.model.TransactionType.SETOR,
                                deskripsi = "Setor ${String.format("%,.0f", beratBasah)} Kg $selectedGrade",
                                tanggal = "Hari Ini",
                                jumlah = -totalValuation.toLong(), // Negative amount for SETOR (potong kasbon)
                                label = "Potong Kasbon"
                            )
                            farmerViewModel.addTransaction(
                                farmerId = selectedFarmer!!.id,
                                transaction = transaction,
                                onSuccess = {
                                    isSaving = false
                                    Toast.makeText(context, "Timbang & Grading Baru Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
                                    onBackClick()
                                },
                                onFailure = { err ->
                                    isSaving = false
                                    Toast.makeText(context, "Berhasil simpan batch, tapi gagal update kasbon: ${err.localizedMessage}", Toast.LENGTH_LONG).show()
                                    onBackClick()
                                }
                            )
                        },
                        onFailure = { err ->
                            isSaving = false
                            Toast.makeText(context, "Gagal membuat batch gudang: ${err.localizedMessage}", Toast.LENGTH_LONG).show()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PinangGreen),
                enabled = !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Scale,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Simpan & Mulai Penjemuran",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
