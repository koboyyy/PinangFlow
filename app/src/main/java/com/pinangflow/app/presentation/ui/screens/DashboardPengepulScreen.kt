package com.pinangflow.app.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.WaterDrop
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
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.ui.components.*
import com.pinangflow.app.domain.model.UnifiedTransaction
import com.pinangflow.app.presentation.viewmodel.AuthViewModel
import com.pinangflow.app.presentation.viewmodel.BatchViewModel
import com.pinangflow.app.presentation.viewmodel.ExportViewModel

@Composable
fun DashboardPengepulScreen(
    modifier: Modifier = Modifier,
    viewModel: BatchViewModel = hiltViewModel(),
    exportViewModel: ExportViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigateToKalkulator: () -> Unit = {},
    onNavigateToEkspor: () -> Unit = {},
    onNavigateToAllTransactions: () -> Unit = {},
    onNavigateToProfil: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val stats by viewModel.stats.collectAsState()
    val farmerTransactions by viewModel.transactions.collectAsState()
    val exportTransactions by exportViewModel.exportTransactions.collectAsState()

    val combinedTransactions = remember(farmerTransactions, exportTransactions) {
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
    val currentUser by authViewModel.currentUser.collectAsState()

    val context = LocalContext.current
    val userName = currentUser?.displayName ?: "Pengepul"

    // Dialog state
    var showDialog by remember { mutableStateOf(false) }
    var inputGradeA by remember { mutableStateOf("") }
    var inputGradeB by remember { mutableStateOf("") }
    var inputGradeC by remember { mutableStateOf("") }

    // Parsing stats with dynamic formatting
    val totalStokBasahVal = (stats["totalStokBasah"] as? Number)?.toDouble() ?: 0.0
    val totalStokKeringVal = (stats["totalStokKering"] as? Number)?.toDouble() ?: 0.0
    val aktifKasbonVal = (stats["aktifKasbon"] as? Number)?.toLong() ?: 0L
    val penyusutanVal = (stats["penyusutan"] as? Number)?.toDouble() ?: 0.0
    val hargaGradeAVal = stats["hargaGradeA"] ?: 0L
    val hargaGradeBVal = stats["hargaGradeB"] ?: 0L
    val hargaGradeCVal = stats["hargaGradeC"] ?: 0L

    val (totalStokBasah, basahUnit) = if (totalStokBasahVal >= 1.0) {
        Pair(String.format("%.1f", totalStokBasahVal).replace(',', '.'), "t")
    } else {
        Pair(String.format("%,d", (totalStokBasahVal * 1000).toLong()).replace(',', '.'), "Kg")
    }

    val (totalStokKering, keringUnit) = if (totalStokKeringVal >= 1.0) {
        Pair(String.format("%.1f", totalStokKeringVal).replace(',', '.'), "t")
    } else {
        Pair(String.format("%,d", (totalStokKeringVal * 1000).toLong()).replace(',', '.'), "Kg")
    }

    val penyusutan = String.format("%.1f", penyusutanVal).replace(',', '.')

    val (aktifKasbon, aktifKasbonUnit) = if (aktifKasbonVal >= 1_000_000_000L) {
        Pair(String.format("%.1f", aktifKasbonVal / 1_000_000_000.0).replace(',', '.'), "M")
    } else if (aktifKasbonVal >= 1_000_000L) {
        Pair(String.format("%.1f", aktifKasbonVal / 1_000_000.0).replace(',', '.'), "jt")
    } else {
        Pair(String.format("%,d", aktifKasbonVal).replace(',', '.'), "")
    }

    val hargaGradeA = if (hargaGradeAVal is Number) {
        String.format("%,d", hargaGradeAVal.toLong()).replace(',', '.')
    } else {
        hargaGradeAVal.toString()
    }

    val hargaGradeB = if (hargaGradeBVal is Number) {
        String.format("%,d", hargaGradeBVal.toLong()).replace(',', '.')
    } else {
        hargaGradeBVal.toString()
    }

    val hargaGradeC = if (hargaGradeCVal is Number) {
        String.format("%,d", hargaGradeCVal.toLong()).replace(',', '.')
    } else {
        hargaGradeCVal.toString()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // === GREEN HEADER ===
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PinangGreen,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onNavigateToProfil() }
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "PENGEPUL",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Halo, $userName!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // === SCROLLABLE CONTENT ===
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 24.dp)
        ) {
            // === HARGA HARIAN PINANG ===
            PriceCard(
                tanggal = "Hari Ini",
                hargaGradeA = hargaGradeA,
                hargaGradeB = hargaGradeB,
                hargaGradeC = hargaGradeC,
                onClick = {
                    inputGradeA = hargaGradeA.replace(".", "")
                    inputGradeB = hargaGradeB.replace(".", "")
                    inputGradeC = hargaGradeC.replace(".", "")
                    showDialog = true
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // === RINGKASAN ===
            Text(
                text = "Ringkasan",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Row 1: Stok Basah + Stok Kering
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    icon = Icons.Outlined.WaterDrop,
                    iconBackgroundColor = IconBgGreen,
                    iconTint = PinangGreen,
                    label = "Total Stok Basah",
                    value = totalStokBasah,
                    unit = basahUnit,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    icon = Icons.Outlined.WbSunny,
                    iconBackgroundColor = IconBgYellow,
                    iconTint = PinangAmber,
                    label = "Total Stok Kering",
                    value = totalStokKering,
                    unit = keringUnit,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Row 2: Kasbon + Penyusutan
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    icon = Icons.Default.Receipt,
                    iconBackgroundColor = IconBgBlue,
                    iconTint = Color(0xFF1976D2),
                    label = "Aktif Kasbon",
                    value = aktifKasbon,
                    prefix = "Rp",
                    unit = aktifKasbonUnit,
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    icon = Icons.AutoMirrored.Filled.TrendingDown,
                    iconBackgroundColor = IconBgRed,
                    iconTint = ErrorRed,
                    label = "Penyusutan",
                    value = penyusutan,
                    unit = "%",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // === TOMBOL TIMBANG & GRADING ===
            Button(
                onClick = onNavigateToKalkulator,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinangGreen
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Scale,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "TIMBANG & GRADING BARU",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // === TOMBOL PENJUALAN EKSPOR ===
            Button(
                onClick = onNavigateToEkspor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinangAmber
                )
            ) {
                Icon(
                    imageVector = Icons.Default.LocalShipping,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "PENJUALAN EKSPOR",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    letterSpacing = 0.5.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // === TRANSAKSI ===
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transaksi",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                TextButton(onClick = onNavigateToAllTransactions) {
                    Text(
                        text = "Lihat Semua",
                        color = PinangGreen,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Transaction items from combined list
            if (combinedTransactions.isNotEmpty()) {
                combinedTransactions.take(5).forEachIndexed { index, tx ->
                    val prefix = if (tx.isIncome) "+ " else "- "
                    val amountText = "${prefix}Rp ${String.format("%,d", kotlin.math.abs(tx.jumlah)).replace(',', '.')}"
                    TransactionItem(
                        avatarInitials = tx.title.take(2).uppercase(),
                        avatarColor = if (tx.isIncome) PinangGreen else PinangAmber,
                        nama = tx.title,
                        detail = tx.detail,
                        jumlah = amountText,
                        waktu = tx.tanggal,
                        isIncome = tx.isIncome
                    )
                    if (index < combinedTransactions.take(5).size - 1) {
                        HorizontalDivider(color = DividerColor, thickness = 1.dp)
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada riwayat transaksi",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }

    // === POP-UP DIALOG UNTUK EDIT HARGA HARIAN ===
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Atur Harga Pinang",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextPrimary
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Perbarui nilai harga beli harian pinang per Kg untuk Grade A, B, dan C.",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )

                    // Input Grade A
                    Text(
                        text = "Harga Grade A (Kualitas Super) / Kg",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    OutlinedTextField(
                        value = inputGradeA,
                        onValueChange = { inputGradeA = it },
                        placeholder = { Text("0") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = CardBorder,
                            focusedBorderColor = PinangGreen
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Input Grade B
                    Text(
                        text = "Harga Grade B (Kualitas Biasa) / Kg",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    OutlinedTextField(
                        value = inputGradeB,
                        onValueChange = { inputGradeB = it },
                        placeholder = { Text("0") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = CardBorder,
                            focusedBorderColor = PinangGreen
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Input Grade C
                    Text(
                        text = "Harga Grade C (Kualitas Rendah) / Kg",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    OutlinedTextField(
                        value = inputGradeC,
                        onValueChange = { inputGradeC = it },
                        placeholder = { Text("0") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = CardBorder,
                            focusedBorderColor = PinangGreen
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val valA = inputGradeA.toLongOrNull()
                        val valB = inputGradeB.toLongOrNull()
                        val valC = inputGradeC.toLongOrNull()
                        if (valA == null || valB == null || valC == null) {
                            Toast.makeText(context, "Harga harus berupa angka valid!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        viewModel.updateDailyPrices(
                            hargaGradeA = valA,
                            hargaGradeB = valB,
                            hargaGradeC = valC,
                            onSuccess = {
                                showDialog = false
                                Toast.makeText(context, "Harga harian berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = {
                                Toast.makeText(context, "Gagal memperbarui harga: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PinangGreen),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Simpan",
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDialog = false },
                    border = BorderStroke(1.dp, CardBorder),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary)
                ) {
                    Text(text = "Batal")
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = BackgroundWhite
        )
    }
}
