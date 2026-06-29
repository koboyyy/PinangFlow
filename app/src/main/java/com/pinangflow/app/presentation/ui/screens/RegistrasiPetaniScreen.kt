package com.pinangflow.app.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.ui.components.TopAppBarGreen
import com.pinangflow.app.presentation.viewmodel.FarmerViewModel

@Composable
fun RegistrasiPetaniScreen(
    modifier: Modifier = Modifier,
    viewModel: FarmerViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var namaLengkap by remember { mutableStateOf("") }
    var nomorWhatsapp by remember { mutableStateOf("") }
    var alamatKebun by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // Header
        TopAppBarGreen(
            title = "Registrasi Petani",
            subtitle = "Tambah mitra/pelanggan baru",
            showBackButton = true,
            onBackClick = onBackClick
        )

        // Form Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // === SUB-HEADING: DATA PRIBADI PETANI ===
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = PinangGreen,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "DATA PRIBADI PETANI",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 1.sp
                )
            }

            // === FIELD 1: NAMA LENGKAP ===
            Text(
                text = "Nama Lengkap",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = namaLengkap,
                onValueChange = { namaLengkap = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = {
                    Text(
                        text = "Masukkan nama petani",
                        color = TextTertiary,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = TextSecondary
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = CardBorder,
                    focusedBorderColor = PinangGreen
                ),
                singleLine = true,
                enabled = !isSaving
            )

            // === FIELD 2: NOMOR WHATSAPP ===
            Text(
                text = "Nomor WhatsApp",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = nomorWhatsapp,
                onValueChange = { nomorWhatsapp = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "08xxxxxxxxxx",
                        color = TextTertiary,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = null,
                        tint = TextSecondary
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = CardBorder,
                    focusedBorderColor = PinangGreen
                ),
                singleLine = true,
                enabled = !isSaving
            )
            // Helper WhatsApp Text
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 6.dp, bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Digunakan untuk mengirim nota otomatis",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            // === FIELD 3: ALAMAT / LOKASI KEBUN ===
            Text(
                text = "Alamat / Lokasi Kebun",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = alamatKebun,
                onValueChange = { alamatKebun = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = {
                    Text(
                        text = "Jl. Raya Pinang No. 12...",
                        color = TextTertiary,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 12.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Map,
                            contentDescription = null,
                            tint = TextSecondary
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = CardBorder,
                    focusedBorderColor = PinangGreen
                ),
                maxLines = 4,
                enabled = !isSaving
            )
        }

        // === BOTTOM BUTTONS BAR ===
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp,
            color = BackgroundWhite
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Batal
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .weight(0.4f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, CardBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = PinangGreen
                    ),
                    enabled = !isSaving
                ) {
                    Text(
                        text = "Batal",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                // Simpan Petani Baru
                Button(
                    onClick = {
                        if (namaLengkap.isBlank() || nomorWhatsapp.isBlank()) {
                            Toast.makeText(context, "Nama dan WhatsApp tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isSaving = true
                        viewModel.addFarmer(
                            nama = namaLengkap,
                            nomorTelepon = nomorWhatsapp,
                            alamat = alamatKebun,
                            luasLahan = 2.0, // default placeholder
                            jenisTanaman = "Pinang", // default placeholder
                            onSuccess = {
                                isSaving = false
                                Toast.makeText(context, "Petani baru berhasil disimpan!", Toast.LENGTH_SHORT).show()
                                onBackClick()
                            },
                            onFailure = {
                                isSaving = false
                                Toast.makeText(context, "Gagal menyimpan: ${it.localizedMessage}", Toast.LENGTH_LONG).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .weight(0.6f)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PinangGreen
                    ),
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
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Simpan Petani Baru",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
