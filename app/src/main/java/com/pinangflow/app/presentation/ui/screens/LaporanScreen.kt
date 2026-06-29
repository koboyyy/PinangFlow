package com.pinangflow.app.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pinangflow.app.domain.model.FarmerTransaction
import com.pinangflow.app.domain.model.TransactionType
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.ui.components.TopAppBarGreen
import com.pinangflow.app.presentation.viewmodel.BatchViewModel
import com.pinangflow.app.presentation.viewmodel.ExportViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun LaporanScreen(
    modifier: Modifier = Modifier,
    viewModel: BatchViewModel = hiltViewModel(),
    exportViewModel: ExportViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    var selectedFilter by remember { mutableStateOf("Semua") }
    val stats by viewModel.stats.collectAsState()
    val allTransactions by viewModel.transactions.collectAsState()
    val allExportTransactions by exportViewModel.exportTransactions.collectAsState()

    // ============================================================
    // TIME-BASED FILTERING
    // ============================================================
    val startOfToday = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    val startOfWeek = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
        }.timeInMillis
    }

    val startOfMonth = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.DAY_OF_MONTH, 1)
        }.timeInMillis
    }

    val filteredTransactions = remember(allTransactions, selectedFilter) {
        when (selectedFilter) {
            "Hari Ini" -> allTransactions.filter { it.timestamp >= startOfToday }
            "Minggu Ini" -> allTransactions.filter { it.timestamp >= startOfWeek }
            "Bulan Ini" -> allTransactions.filter { it.timestamp >= startOfMonth }
            else -> allTransactions // "Semua"
        }
    }

    val filteredExportTransactions = remember(allExportTransactions, selectedFilter) {
        val sdfInput = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val withTimestamps = allExportTransactions.map { tx ->
            val ts = try { sdfInput.parse(tx.tanggalKeluar)?.time ?: 0L } catch (e: Exception) { 0L }
            tx to ts
        }

        val filtered = when (selectedFilter) {
            "Hari Ini" -> withTimestamps.filter { it.second >= startOfToday }
            "Minggu Ini" -> withTimestamps.filter { it.second >= startOfWeek }
            "Bulan Ini" -> withTimestamps.filter { it.second >= startOfMonth }
            else -> withTimestamps // "Semua"
        }
        filtered.map { it.first }
    }

    // ============================================================
    // COMPUTE STATS FROM FILTERED TRANSACTIONS
    // ============================================================
    val computedStats = remember(filteredTransactions, filteredExportTransactions) {
        var uangKeluar = 0L
        var kasbonTerbayar = 0L
        var volumePembelian = 0L
        var totalSetor = 0L
        var totalKasbon = 0L
        var jumlahTransaksi = 0

        var pendapatanEkspor = 0L
        var volumeEkspor = 0L

        filteredTransactions.forEach { tx ->
            jumlahTransaksi++
            when (tx.jenis) {
                TransactionType.SETOR -> {
                    uangKeluar += kotlin.math.abs(tx.jumlah)
                    // Extract weight from deskripsi
                    val regex = Regex("""Setor\s+([\d.,]+)\s*Kg""")
                    val match = regex.find(tx.deskripsi)
                    val weight = match?.groupValues?.get(1)
                        ?.replace(".", "")?.replace(",", ".")
                        ?.toDoubleOrNull() ?: 0.0
                    volumePembelian += weight.toLong()
                    totalSetor += kotlin.math.abs(tx.jumlah)
                }
                TransactionType.KASBON_BARU -> {
                    uangKeluar += tx.jumlah
                    totalKasbon += tx.jumlah
                }
                TransactionType.BAYAR -> {
                    kasbonTerbayar += kotlin.math.abs(tx.jumlah)
                }
            }
        }

        filteredExportTransactions.forEach { tx ->
            jumlahTransaksi++
            pendapatanEkspor += tx.totalPendapatan
            volumeEkspor += tx.beratJualKg.toLong()
        }

        mapOf(
            "uangKeluar" to uangKeluar,
            "kasbonTerbayar" to kasbonTerbayar,
            "volumePembelian" to volumePembelian,
            "totalSetor" to totalSetor,
            "totalKasbon" to totalKasbon,
            "jumlahTransaksi" to jumlahTransaksi,
            "pendapatanEkspor" to pendapatanEkspor,
            "volumeEkspor" to volumeEkspor
        )
    }

    val uangKeluarVal = (computedStats["uangKeluar"] as? Number)?.toLong() ?: 0L
    val kasbonTerbayarVal = (computedStats["kasbonTerbayar"] as? Number)?.toLong() ?: 0L
    val volumePembelianVal = (computedStats["volumePembelian"] as? Number)?.toLong() ?: 0L
    val totalSetorVal = (computedStats["totalSetor"] as? Number)?.toLong() ?: 0L
    val totalKasbonVal = (computedStats["totalKasbon"] as? Number)?.toLong() ?: 0L
    val jumlahTransaksiVal = (computedStats["jumlahTransaksi"] as? Number)?.toInt() ?: 0
    val pendapatanEksporVal = (computedStats["pendapatanEkspor"] as? Number)?.toLong() ?: 0L
    val volumeEksporVal = (computedStats["volumeEkspor"] as? Number)?.toLong() ?: 0L

    // ============================================================
    // BAR CHART DATA (per-day aggregation)
    // ============================================================
    val chartBarData = remember(filteredTransactions, selectedFilter) {
        buildChartData(filteredTransactions, selectedFilter)
    }

    val maxChartValue = chartBarData.maxOfOrNull { it.rawValue } ?: 0.0

    // ============================================================
    // UI
    // ============================================================
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // Top App Bar
        TopAppBarGreen(
            title = "Laporan Bisnis",
            subtitle = "Ringkasan transaksi & operasional"
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // === TIME FILTERS ROW ===
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf("Hari Ini", "Minggu Ini", "Bulan Ini", "Semua").forEach { filter ->
                    val isActive = selectedFilter == filter
                    Button(
                        onClick = { selectedFilter = filter },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isActive) PinangGreen else Color(0xFFF0F0F0),
                            contentColor = if (isActive) Color.White else TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = filter,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // === CHART CARD ===
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardSurface),
                border = BorderStroke(1.dp, CardBorder.copy(alpha = 0.4f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Volume Pembelian (Kg)",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Total: ${String.format("%,d", volumePembelianVal).replace(',', '.')} Kg",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Dynamic Bar Chart
                    if (chartBarData.isEmpty() || maxChartValue == 0.0) {
                        // Empty state for chart
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Belum ada data pembelian untuk periode ini.",
                                fontSize = 12.sp,
                                color = TextTertiary,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            chartBarData.forEach { data ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Value label on top
                                    if (data.rawValue > 0) {
                                        Text(
                                            text = formatChartLabel(data.rawValue),
                                            fontSize = 9.sp,
                                            color = if (data.isHighest) PinangGreen else TextTertiary,
                                            fontWeight = if (data.isHighest) FontWeight.Bold else FontWeight.Normal
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                    }

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth(),
                                        contentAlignment = Alignment.BottomCenter
                                    ) {
                                        val fraction = (data.rawValue / maxChartValue).toFloat()
                                            .coerceIn(0.05f, 1f)
                                        val barColor = if (data.isHighest) PinangGreen else PinangGreen.copy(alpha = 0.35f)
                                        Box(
                                            modifier = Modifier
                                                .width(16.dp)
                                                .fillMaxHeight(fraction)
                                                .background(
                                                    color = barColor,
                                                    shape = RoundedCornerShape(
                                                        topStart = 4.dp,
                                                        topEnd = 4.dp
                                                    )
                                                )
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    // Day/Period Label
                                    Text(
                                        text = data.label,
                                        fontSize = 10.sp,
                                        color = if (data.isHighest) TextPrimary else TextTertiary,
                                        fontWeight = if (data.isHighest) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = DividerColor, thickness = 1.dp)
                }
            }

            // === EXPORT STATS GRID ===
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Pendapatan Ekspor
                ReportStatCard(
                    title = "Pendapatan Ekspor",
                    value = formatCurrency(pendapatanEksporVal),
                    valueColor = PinangGreen,
                    icon = Icons.AutoMirrored.Filled.TrendingUp,
                    iconTint = PinangGreen,
                    iconBg = IconBgGreen,
                    modifier = Modifier.weight(1f)
                )

                // Volume Terjual Ekspor
                ReportStatCard(
                    title = "Volume Terjual",
                    value = "${String.format("%,d", volumeEksporVal).replace(',', '.')} Kg",
                    valueColor = Color(0xFF1565C0),
                    icon = Icons.Default.Scale,
                    iconTint = Color(0xFF1565C0),
                    iconBg = IconBgBlue,
                    modifier = Modifier.weight(1f)
                )
            }

            // === SUMMARY GRID - ROW 1 ===
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Uang Keluar
                ReportStatCard(
                    title = "Total Uang Keluar",
                    value = formatCurrency(uangKeluarVal),
                    valueColor = ErrorRed,
                    icon = Icons.AutoMirrored.Filled.TrendingDown,
                    iconTint = ErrorRed,
                    iconBg = IconBgRed,
                    modifier = Modifier.weight(1f)
                )

                // Kasbon Terbayar
                ReportStatCard(
                    title = "Kasbon Terbayar",
                    value = formatCurrency(kasbonTerbayarVal),
                    valueColor = PinangGreen,
                    icon = Icons.Default.Payments,
                    iconTint = PinangGreen,
                    iconBg = IconBgGreen,
                    modifier = Modifier.weight(1f)
                )
            }

            // === SUMMARY GRID - ROW 2 ===
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Volume Pembelian
                ReportStatCard(
                    title = "Volume Pembelian",
                    value = "${String.format("%,d", volumePembelianVal).replace(',', '.')} Kg",
                    valueColor = TextPrimary,
                    icon = Icons.Default.Scale,
                    iconTint = TextSecondary,
                    iconBg = Color(0xFFF5F5F5),
                    modifier = Modifier.weight(1f)
                )

                // Total Kasbon Diberikan
                ReportStatCard(
                    title = "Total Kasbon Keluar",
                    value = formatCurrency(totalKasbonVal),
                    valueColor = Color(0xFFF57C00),
                    icon = Icons.AutoMirrored.Filled.TrendingDown,
                    iconTint = Color(0xFFF57C00),
                    iconBg = HutangOrangeBg,
                    modifier = Modifier.weight(1f)
                )
            }

            // === SUMMARY GRID - ROW 3 ===
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Total Biaya Pembelian Pinang
                ReportStatCard(
                    title = "Biaya Beli Pinang",
                    value = formatCurrency(totalSetorVal),
                    valueColor = Color(0xFF1565C0),
                    icon = Icons.Default.ShoppingCart,
                    iconTint = Color(0xFF1565C0),
                    iconBg = IconBgBlue,
                    modifier = Modifier.weight(1f)
                )

                // Jumlah Transaksi
                ReportStatCard(
                    title = "Jumlah Transaksi",
                    value = "$jumlahTransaksiVal transaksi",
                    valueColor = TextPrimary,
                    icon = Icons.Default.Inbox,
                    iconTint = PinangGreen,
                    iconBg = IconBgGreen,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // === GUDANG STATS (from global, always show) ===
            val totalStokBasahVal = (stats["totalStokBasah"] as? Number)?.toDouble() ?: 0.0
            val totalStokKeringVal = (stats["totalStokKering"] as? Number)?.toDouble() ?: 0.0
            val penyusutanVal = (stats["penyusutan"] as? Number)?.toDouble() ?: 0.0
            val aktifKasbonVal = (stats["aktifKasbon"] as? Number)?.toLong() ?: 0L

            val basahStr = if (totalStokBasahVal >= 1.0) {
                "${String.format("%.1f", totalStokBasahVal).replace(',', '.')} Ton"
            } else {
                "${String.format("%,d", (totalStokBasahVal * 1000).toLong()).replace(',', '.')} Kg"
            }

            val keringStr = if (totalStokKeringVal >= 1.0) {
                "${String.format("%.1f", totalStokKeringVal).replace(',', '.')} Ton"
            } else {
                "${String.format("%,d", (totalStokKeringVal * 1000).toLong()).replace(',', '.')} Kg"
            }


            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = PinangGreenSurface.copy(alpha = 0.5f)),
                border = BorderStroke(1.dp, PinangGreen.copy(alpha = 0.2f)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Ringkasan Gudang & Kasbon (Total Keseluruhan)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PinangGreen
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        GudangMiniStat(
                            label = "Stok Basah",
                            value = basahStr,
                            modifier = Modifier.weight(1f)
                        )
                        GudangMiniStat(
                            label = "Stok Kering",
                            value = keringStr,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        GudangMiniStat(
                            label = "Penyusutan",
                            value = "${String.format("%.1f", penyusutanVal)}%",
                            modifier = Modifier.weight(1f)
                        )
                        GudangMiniStat(
                            label = "Aktif Kasbon",
                            value = formatCurrency(aktifKasbonVal),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// ============================================================
// CHART BAR DATA MODEL & BUILDER
// ============================================================

data class ChartBarData(
    val label: String,
    val rawValue: Double,
    val isHighest: Boolean
)

private fun buildChartData(
    transactions: List<FarmerTransaction>,
    filter: String
): List<ChartBarData> {
    // Only SETOR transactions contribute to volume
    val setorTxs = transactions.filter { it.jenis == TransactionType.SETOR }

    if (setorTxs.isEmpty()) return emptyList()

    val regex = Regex("""Setor\s+([\d.,]+)\s*Kg""")

    fun extractWeight(deskripsi: String): Double {
        val match = regex.find(deskripsi)
        return match?.groupValues?.get(1)
            ?.replace(".", "")?.replace(",", ".")
            ?.toDoubleOrNull() ?: 0.0
    }

    return when (filter) {
        "Hari Ini" -> {
            // Group by hour (show 6 time blocks: 06-09, 09-12, 12-15, 15-18, 18-21, 21-24)
            val blocks = listOf("06-09", "09-12", "12-15", "15-18", "18-21", "21+")
            val ranges = listOf(6..8, 9..11, 12..14, 15..17, 18..20, 21..23)

            val values = ranges.map { range ->
                setorTxs.filter { tx ->
                    val cal = Calendar.getInstance().apply { timeInMillis = tx.timestamp }
                    cal.get(Calendar.HOUR_OF_DAY) in range
                }.sumOf { extractWeight(it.deskripsi) }
            }

            val maxVal = values.max()
            blocks.zip(values).map { (label, value) ->
                ChartBarData(label, value, value == maxVal && value > 0)
            }
        }
        "Minggu Ini" -> {
            // Group by day of week (Sen-Min)
            val dayNames = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")
            val dayMap = mapOf(
                Calendar.MONDAY to 0, Calendar.TUESDAY to 1,
                Calendar.WEDNESDAY to 2, Calendar.THURSDAY to 3,
                Calendar.FRIDAY to 4, Calendar.SATURDAY to 5,
                Calendar.SUNDAY to 6
            )

            val values = MutableList(7) { 0.0 }
            setorTxs.forEach { tx ->
                val cal = Calendar.getInstance().apply { timeInMillis = tx.timestamp }
                val dayIndex = dayMap[cal.get(Calendar.DAY_OF_WEEK)] ?: 0
                values[dayIndex] += extractWeight(tx.deskripsi)
            }

            val maxVal = values.max()
            dayNames.mapIndexed { i, name ->
                ChartBarData(name, values[i], values[i] == maxVal && values[i] > 0)
            }
        }
        "Bulan Ini" -> {
            // Group by week of month (Minggu 1-4/5)
            val weekValues = mutableMapOf<Int, Double>()
            setorTxs.forEach { tx ->
                val cal = Calendar.getInstance().apply { timeInMillis = tx.timestamp }
                val weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH).coerceIn(1, 5)
                weekValues[weekOfMonth] = (weekValues[weekOfMonth] ?: 0.0) + extractWeight(tx.deskripsi)
            }

            val maxWeek = Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_MONTH).coerceIn(1, 5)
            val values = (1..maxWeek).map { weekValues[it] ?: 0.0 }
            val maxVal = values.maxOrNull() ?: 0.0

            (1..maxWeek).map { week ->
                ChartBarData("Mgg $week", weekValues[week] ?: 0.0, (weekValues[week] ?: 0.0) == maxVal && maxVal > 0)
            }
        }
        else -> {
            // "Semua" - group by month
            val monthValues = mutableMapOf<String, Double>()
            val monthOrder = mutableListOf<String>()
            setorTxs.forEach { tx ->
                val cal = Calendar.getInstance().apply { timeInMillis = tx.timestamp }
                val monthKey = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale("id")) ?: "?"
                val year = cal.get(Calendar.YEAR).toString().takeLast(2)
                val key = "$monthKey'$year"
                if (key !in monthOrder) monthOrder.add(key)
                monthValues[key] = (monthValues[key] ?: 0.0) + extractWeight(tx.deskripsi)
            }

            val maxVal = monthValues.values.maxOrNull() ?: 0.0
            // Show last 7 months max
            val displayMonths = monthOrder.takeLast(7)
            displayMonths.map { key ->
                ChartBarData(key, monthValues[key] ?: 0.0, (monthValues[key] ?: 0.0) == maxVal && maxVal > 0)
            }
        }
    }
}

// ============================================================
// UTILITY FUNCTIONS
// ============================================================

private fun formatCurrency(value: Long): String {
    val absValue = kotlin.math.abs(value)
    return when {
        absValue >= 1_000_000_000 -> "Rp ${String.format("%.1f", absValue / 1_000_000_000.0).replace(',', '.')} M"
        absValue >= 1_000_000 -> "Rp ${String.format("%.1f", absValue / 1_000_000.0).replace(',', '.')} jt"
        else -> "Rp ${String.format("%,d", absValue).replace(',', '.')}"
    }
}

private fun formatChartLabel(value: Double): String {
    return when {
        value >= 1000 -> "${String.format("%.0f", value / 1000)}rb"
        value >= 1 -> String.format("%.0f", value)
        else -> ""
    }
}

// ============================================================
// COMPOSABLE COMPONENTS
// ============================================================

@Composable
fun ReportStatCard(
    title: String,
    value: String,
    valueColor: Color,
    icon: ImageVector,
    iconTint: Color,
    iconBg: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        border = BorderStroke(1.dp, CardBorder.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
        }
    }
}

@Composable
private fun GudangMiniStat(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = label,
                fontSize = 11.sp,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }
    }
}
