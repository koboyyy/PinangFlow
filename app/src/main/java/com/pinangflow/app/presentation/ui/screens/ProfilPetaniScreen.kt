package com.pinangflow.app.presentation.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pinangflow.app.domain.model.Farmer
import com.pinangflow.app.domain.model.FarmerTransaction
import com.pinangflow.app.domain.model.TransactionType
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.ui.components.*
import com.pinangflow.app.presentation.viewmodel.FarmerViewModel

@Composable
fun ProfilPetaniScreen(
    petaniId: String,
    modifier: Modifier = Modifier,
    viewModel: FarmerViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val farmerState = remember(petaniId) { viewModel.getFarmer(petaniId) }.collectAsState()
    val transactionsState = remember(petaniId) { viewModel.getFarmerTransactions(petaniId) }.collectAsState()

    val currentFarmer = farmerState.value
    val transactionsList = transactionsState.value

    // Popups state
    var showKasbonDialog by remember { mutableStateOf(false) }
    var showBayarDialog by remember { mutableStateOf(false) }

    var inputAmount by remember { mutableStateOf("") }
    var inputDesc by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // Header
        TopAppBarGreen(
            title = "Profil Petani",
            showBackButton = true,
            onBackClick = onBackClick,
            trailingIcon = Icons.Default.Search,
            onTrailingClick = { }
        )

        if (currentFarmer == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = PinangGreen)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Data petani sedang dimuat atau tidak ditemukan...",
                        color = TextSecondary,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                // === KARTU PROFIL PETANI ===
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = PinangGreenSurface),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(PinangGreen.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = PinangGreen,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = currentFarmer.nama,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = TextPrimary
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = currentFarmer.nomorTelepon,
                                    fontSize = 13.sp,
                                    color = TextSecondary
                                )
                            }
                        }

                        // WhatsApp Chat Button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(PinangGreen)
                                .clickable {
                                    try {
                                        val formattedPhone = currentFarmer.nomorTelepon
                                            .replace("-", "")
                                            .replace(" ", "")
                                        val url = "https://api.whatsapp.com/send?phone=$formattedPhone"
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Gagal membuka WhatsApp: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Chat,
                                contentDescription = "Chat WhatsApp",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // === TOTAL DISETOR & SISA KASBON ===
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Total Disetor
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurface),
                        elevation = CardDefaults.cardElevation(1.dp),
                        border = BorderStroke(1.dp, CardBorder.copy(alpha = 0.4f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Receipt,
                                    contentDescription = null,
                                    tint = TextSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Total Disetor",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = String.format("%,.0f", currentFarmer.totalDisetor).replace(',', '.'),
                                    fontSize = 24.sp,
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

                    // Sisa Kasbon
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = CardSurface),
                        elevation = CardDefaults.cardElevation(1.dp),
                        border = BorderStroke(1.dp, KasbonRed.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(KasbonRedBg)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Outlined.Payments,
                                            contentDescription = null,
                                            tint = KasbonRed,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Sisa Kasbon",
                                            fontSize = 11.sp,
                                            color = KasbonRed,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Rp " + String.format("%,.0f", currentFarmer.sisaKasbon).replace(',', '.'),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = KasbonRed
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // === TOMBOL AKSI ===
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Kasbon Baru
                    OutlinedButton(
                        onClick = {
                            inputAmount = ""
                            inputDesc = "Kasbon Baru"
                            showKasbonDialog = true
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(72.dp),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.5.dp, KasbonRed.copy(alpha = 0.5f)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = KasbonRedBg.copy(alpha = 0.3f)
                        )
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.PostAdd,
                                contentDescription = null,
                                tint = KasbonRed,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Kasbon Baru",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = KasbonRed
                            )
                        }
                    }

                    // Terima Bayar
                    OutlinedButton(
                        onClick = {
                            inputAmount = ""
                            inputDesc = "Bayar Kasbon"
                            showBayarDialog = true
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(72.dp),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.5.dp, CardBorder),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = CardSurface
                        )
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Outlined.Payments,
                                contentDescription = null,
                                tint = TextPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Terima Bayar",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // === RIWAYAT TRANSAKSI ===
                Text(
                    text = "Riwayat Transaksi Terakhir",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (transactionsList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Belum ada riwayat transaksi untuk petani ini.",
                            color = TextSecondary,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    transactionsList.forEachIndexed { index, transaction ->
                        FarmerTransactionItem(transaction = transaction)
                        if (index < transactionsList.lastIndex) {
                            HorizontalDivider(
                                color = DividerColor,
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // === POP-UP DIALOG KASBON BARU ===
    if (showKasbonDialog && currentFarmer != null) {
        AlertDialog(
            onDismissRequest = { showKasbonDialog = false },
            title = {
                Text(
                    text = "Catat Kasbon Baru",
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
                        text = "Masukkan nilai pinjaman kasbon baru untuk petani ${currentFarmer.nama}.",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )

                    // Jumlah Kasbon
                    Text(
                        text = "Jumlah Kasbon (Rp)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    OutlinedTextField(
                        value = inputAmount,
                        onValueChange = { inputAmount = it },
                        placeholder = { Text("Contoh: 500000") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = CardBorder,
                            focusedBorderColor = PinangGreen
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Deskripsi
                    Text(
                        text = "Keterangan / Deskripsi",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    OutlinedTextField(
                        value = inputDesc,
                        onValueChange = { inputDesc = it },
                        placeholder = { Text("Kasbon Baru") },
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
                        val amount = inputAmount.toLongOrNull()
                        if (amount == null || amount <= 0) {
                            Toast.makeText(context, "Masukkan jumlah kasbon valid!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isSubmitting = true
                        val transaction = FarmerTransaction(
                            id = java.util.UUID.randomUUID().toString(),
                            jenis = TransactionType.KASBON_BARU,
                            deskripsi = inputDesc.ifBlank { "Kasbon Baru" },
                            tanggal = "Hari Ini",
                            jumlah = amount, // Positive values add to kasbon
                            label = "Hutang"
                        )
                        viewModel.addTransaction(
                            farmerId = currentFarmer.id,
                            transaction = transaction,
                            onSuccess = {
                                isSubmitting = false
                                showKasbonDialog = false
                                Toast.makeText(context, "Kasbon baru berhasil disimpan!", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = {
                                isSubmitting = false
                                Toast.makeText(context, "Gagal mencatat kasbon: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PinangGreen),
                    shape = RoundedCornerShape(10.dp),
                    enabled = !isSubmitting
                ) {
                    Text(text = "Simpan", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showKasbonDialog = false },
                    border = BorderStroke(1.dp, CardBorder),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                    enabled = !isSubmitting
                ) {
                    Text(text = "Batal")
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = BackgroundWhite
        )
    }

    // === POP-UP DIALOG TERIMA BAYAR ===
    if (showBayarDialog && currentFarmer != null) {
        AlertDialog(
            onDismissRequest = { showBayarDialog = false },
            title = {
                Text(
                    text = "Terima Pembayaran Kasbon",
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
                        text = "Catat penerimaan uang tunai untuk pemotongan/pembayaran sisa kasbon ${currentFarmer.nama}.",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )

                    // Jumlah Bayar
                    Text(
                        text = "Jumlah Pembayaran (Rp)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    OutlinedTextField(
                        value = inputAmount,
                        onValueChange = { inputAmount = it },
                        placeholder = { Text("Contoh: 200000") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = CardBorder,
                            focusedBorderColor = PinangGreen
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Deskripsi
                    Text(
                        text = "Keterangan / Deskripsi",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    OutlinedTextField(
                        value = inputDesc,
                        onValueChange = { inputDesc = it },
                        placeholder = { Text("Bayar Kasbon") },
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
                        val amount = inputAmount.toLongOrNull()
                        if (amount == null || amount <= 0) {
                            Toast.makeText(context, "Masukkan jumlah pembayaran valid!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isSubmitting = true
                        val transaction = FarmerTransaction(
                            id = java.util.UUID.randomUUID().toString(),
                            jenis = TransactionType.BAYAR,
                            deskripsi = inputDesc.ifBlank { "Bayar Kasbon" },
                            tanggal = "Hari Ini",
                            jumlah = -amount, // Negative values decrease kasbon
                            label = "Cash"
                        )
                        viewModel.addTransaction(
                            farmerId = currentFarmer.id,
                            transaction = transaction,
                            onSuccess = {
                                isSubmitting = false
                                showBayarDialog = false
                                Toast.makeText(context, "Pembayaran kasbon berhasil disimpan!", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = {
                                isSubmitting = false
                                Toast.makeText(context, "Gagal mencatat pembayaran: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PinangGreen),
                    shape = RoundedCornerShape(10.dp),
                    enabled = !isSubmitting
                ) {
                    Text(text = "Simpan", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showBayarDialog = false },
                    border = BorderStroke(1.dp, CardBorder),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                    enabled = !isSubmitting
                ) {
                    Text(text = "Batal")
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = BackgroundWhite
        )
    }
}

@Composable
private fun FarmerTransactionItem(
    transaction: FarmerTransaction,
    modifier: Modifier = Modifier
) {
    val (iconColor, iconBg, icon) = when (transaction.jenis) {
        TransactionType.SETOR -> Triple(PinangGreen, IconBgGreen, Icons.Default.CallReceived)
        TransactionType.BAYAR -> Triple(CashGreen, IconBgGreen, Icons.Default.AttachMoney)
        TransactionType.KASBON_BARU -> Triple(KasbonRed, IconBgRed, Icons.Outlined.Receipt)
    }

    val labelColor = when (transaction.label) {
        "Potong Kasbon" -> KasbonRed
        "Cash" -> CashGreen
        "Hutang" -> HutangOrange
        else -> TextSecondary
    }

    val labelBg = when (transaction.label) {
        "Potong Kasbon" -> KasbonRedBg
        "Cash" -> IconBgGreen
        "Hutang" -> HutangOrangeBg
        else -> DividerColor
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(0.5.dp),
        border = BorderStroke(1.dp, CardBorder.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Description + Date
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.deskripsi,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = TextPrimary
                )
                Text(
                    text = transaction.tanggal,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            // Amount + Label
            Column(horizontalAlignment = Alignment.End) {
                val prefix = if (transaction.jumlah >= 0) "+ " else "- "
                val amountText = "${prefix}Rp ${formatRupiah(kotlin.math.abs(transaction.jumlah))}"
                Text(
                    text = amountText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (transaction.jumlah >= 0) HutangOrange else CashGreen
                )
                Spacer(modifier = Modifier.height(2.dp))
                LabelBadge(
                    text = transaction.label,
                    backgroundColor = labelBg,
                    textColor = labelColor
                )
            }
        }
    }
}

private fun formatRupiah(amount: Long): String {
    return String.format("%,d", amount).replace(',', '.')
}
