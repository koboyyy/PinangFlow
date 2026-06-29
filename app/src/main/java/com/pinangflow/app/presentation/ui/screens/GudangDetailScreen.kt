package com.pinangflow.app.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pinangflow.app.domain.model.Batch
import com.pinangflow.app.domain.model.BatchStatus
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.ui.components.*
import com.pinangflow.app.presentation.viewmodel.BatchViewModel

@Composable
fun GudangDetailScreen(
    batchId: String,
    modifier: Modifier = Modifier,
    viewModel: BatchViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var beratKeringInput by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val firestoreBatchState = remember(batchId) { viewModel.getBatch(batchId) }.collectAsState()
    val batch = firestoreBatchState.value

    val isCompleted = batch?.status == BatchStatus.SELESAI

    // Pre-fill weight input if batch is completed
    LaunchedEffect(batch) {
        if (batch != null && batch.status == BatchStatus.SELESAI && beratKeringInput.isBlank()) {
            beratKeringInput = if (batch.beratKering == batch.beratKering.toLong().toDouble()) {
                String.format("%.0f", batch.beratKering)
            } else {
                batch.beratKering.toString()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // Header
        TopAppBarGreen(
            title = "Detail Penjemuran",
            subtitle = "Kalkulator penyusutan & update stok kering",
            showBackButton = true,
            onBackClick = onBackClick,
            trailingIcon = Icons.Default.Settings,
            onTrailingClick = { }
        )

        if (batch == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = PinangGreen)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Memuat data detail penjemuran...",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            val beratBasah = batch.beratBasah
            val beratKeringValue = beratKeringInput.toDoubleOrNull() ?: 0.0
            val estimasiPenyusutan = if (beratKeringValue > 0) {
                ((beratBasah - beratKeringValue) / beratBasah) * 100
            } else 0.0
            val kehilanganBerat = if (beratKeringValue > 0) beratBasah - beratKeringValue else 0.0

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                // === BATCH INFO CARD ===
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CardSurface),
                    elevation = CardDefaults.cardElevation(1.dp),
                    border = BorderStroke(1.dp, CardBorder.copy(alpha = 0.4f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Batch #${batch.nomorBatch}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = TextPrimary
                            )
                            StatusBadge(type = if (isCompleted) BadgeType.SELESAI else BadgeType.PROSES)
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = if (isCompleted && batch.tanggalSelesai != null) {
                                "MASUK: ${batch.tanggalMasuk.uppercase()} | SELESAI: ${batch.tanggalSelesai.uppercase()}"
                            } else {
                                "MASUK: ${batch.tanggalMasuk.uppercase()}"
                            },
                            fontSize = 12.sp,
                            color = TextSecondary,
                            letterSpacing = 0.5.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Total Basah Awal
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = ShrinkageYellow.copy(alpha = 0.5f)),
                            elevation = CardDefaults.cardElevation(0.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(IconBgYellow),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.WbSunny,
                                        contentDescription = null,
                                        tint = PinangAmber,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Total Basah Awal",
                                        fontSize = 12.sp,
                                        color = TextSecondary
                                    )
                                    Row(verticalAlignment = Alignment.Bottom) {
                                        Text(
                                            text = String.format("%,.0f", beratBasah),
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                        Text(
                                            text = " Kg",
                                            fontSize = 14.sp,
                                            color = TextSecondary,
                                            modifier = Modifier.padding(bottom = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // === INPUT BERAT KERING ===
                Text(
                    text = if (isCompleted) "Berat Setelah Kering (Sudah Terkunci)" else "Input Berat Setelah Kering (Kg)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = beratKeringInput,
                    onValueChange = { beratKeringInput = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "0.00",
                            fontSize = 28.sp,
                            color = TextTertiary
                        )
                    },
                    suffix = {
                        Text(
                            text = "Kg",
                            fontSize = 16.sp,
                            color = TextSecondary
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = CardBorder,
                        focusedBorderColor = PinangGreen,
                        disabledBorderColor = CardBorder,
                        disabledTextColor = TextPrimary
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    ),
                    singleLine = true,
                    enabled = !isSaving && !isCompleted
                )

                Spacer(modifier = Modifier.height(20.dp))

                // === HASIL KALKULASI OTOMATIS ===
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = ShrinkageYellow.copy(alpha = 0.4f)),
                    elevation = CardDefaults.cardElevation(0.dp)
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
                                text = "Hasil Kalkulasi Otomatis",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PinangGreen
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Estimasi Penyusutan
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "Estimasi Penyusutan",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${String.format("%.1f", estimasiPenyusutan).replace(',', '.')}%",
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ShrinkageYellowText
                                    )
                                }
                            }

                            // Kehilangan Berat
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "Kehilangan Berat",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.Bottom) {
                                        Text(
                                            text = String.format("%.1f", kehilanganBerat).replace(',', '.'),
                                            fontSize = 26.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1565C0)
                                        )
                                        Text(
                                            text = " Kg",
                                            fontSize = 14.sp,
                                            color = TextSecondary,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "* Formula: (Berat Basah - Berat Kering) / Berat Basah. Nilai ini mempengaruhi valuasi penyusutan stok gudang secara real-time.",
                            fontSize = 11.sp,
                            color = TextSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // === INFO AREA & SUHU ===
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = TextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = batch.areaJemur,
                                fontSize = 12.sp,
                                color = TextPrimary
                            )
                        }
                    }

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Thermostat,
                                contentDescription = null,
                                tint = TextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = batch.suhu,
                                fontSize = 12.sp,
                                color = TextPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // === BOTTOM BUTTON ===
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = BackgroundWhite
            ) {
                Button(
                    onClick = {
                        if (beratKeringInput.isBlank()) {
                            Toast.makeText(context, "Input berat kering tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val weight = beratKeringInput.toDoubleOrNull()
                        if (weight == null || weight <= 0) {
                            Toast.makeText(context, "Nilai berat kering tidak valid!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isSaving = true
                        viewModel.updateBatchDryWeight(
                            batchId = batch.id,
                            dryWeight = weight,
                            onSuccess = {
                                isSaving = false
                                Toast.makeText(context, "Batch Gudang berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                                onBackClick()
                            },
                            onFailure = {
                                isSaving = false
                                Toast.makeText(context, "Gagal memperbarui: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCompleted) DividerColor else PinangGreen,
                        contentColor = if (isCompleted) TextSecondary else Color.White
                    ),
                    enabled = !isSaving && !isCompleted
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isCompleted) "Penjemuran Selesai" else "Simpan & Update Stok Kering",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
