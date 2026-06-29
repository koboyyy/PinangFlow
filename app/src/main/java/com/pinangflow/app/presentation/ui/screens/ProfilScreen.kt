package com.pinangflow.app.presentation.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pinangflow.app.domain.model.PengepulProfile
import com.pinangflow.app.presentation.theme.*
import com.pinangflow.app.presentation.viewmodel.AuthViewModel
import com.pinangflow.app.presentation.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilScreen(
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    onLogout: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val profileState by profileViewModel.profile.collectAsState()
    
    // Local state for forms
    var namaPemilik by remember { mutableStateOf("") }
    var alamatGudang by remember { mutableStateOf("") }
    var nomorWaLaporan by remember { mutableStateOf("") }
    var tokenWaGateway by remember { mutableStateOf("") }
    var showToken by remember { mutableStateOf(false) }
    
    var isTestingConnection by remember { mutableStateOf(false) }
    var isBotActive by remember { mutableStateOf(false) }
    var showFonnteTutorial by remember { mutableStateOf(false) }

    // Update form when profile state changes
    LaunchedEffect(profileState) {
        profileState?.let {
            namaPemilik = it.namaPemilik
            alamatGudang = it.alamatGudang
            nomorWaLaporan = it.nomorWaLaporan
            tokenWaGateway = it.tokenWaGateway
        }
    }

    val namaLapak = profileState?.namaLapak?.takeIf { it.isNotBlank() } ?: "Lapak Pengepul"
    val uidPrefix = profileState?.uid?.take(5)?.uppercase() ?: "00000"

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // === HEADER & AVATAR CARD (OVERLAPPING) ===
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Green Background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(PinangGreen)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Profil Lapak & Pengaturan",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    IconButton(onClick = {
                        authViewModel.logout()
                        onLogout()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Keluar",
                            tint = Color.White
                        )
                    }
                }
            }

            // White Avatar Card (Overlapping)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 80.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Circle House Icon
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(IconBgGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = null,
                            tint = PinangGreen,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = namaLapak,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "ID Tenant: T-$uidPrefix",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Edit icon
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(DividerColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profil",
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // === SCROLLABLE FORM ===
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Main Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Nama Pemilik
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Nama Pemilik", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = namaPemilik,
                            onValueChange = { namaPemilik = it },
                            placeholder = { Text("Masukkan nama pemilik", color = TextTertiary) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PinangGreen,
                                unfocusedBorderColor = CardBorder,
                            )
                        )
                    }

                    // Alamat Gudang
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Alamat Gudang", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = alamatGudang,
                            onValueChange = { alamatGudang = it },
                            placeholder = { Text("Contoh: Jl. Lintas Sumatera KM. 12", color = TextTertiary) },
                            shape = RoundedCornerShape(12.dp),
                            minLines = 3,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PinangGreen,
                                unfocusedBorderColor = CardBorder,
                            )
                        )
                    }

                    // Nomor WA Laporan
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.Message, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Nomor WA Laporan", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = nomorWaLaporan,
                            onValueChange = { nomorWaLaporan = it },
                            placeholder = { Text("81234567890", color = TextTertiary) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Text(text = "+62", color = TextSecondary, fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 12.dp))
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PinangGreen,
                                unfocusedBorderColor = CardBorder,
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row {
                            Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = TextTertiary, modifier = Modifier.size(14.dp).padding(top = 2.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Nomor ini akan otomatis menerima laporan ringkasan setiap kali ada transaksi penjualan ekspor/pinang keluar.",
                                fontSize = 11.sp,
                                color = TextTertiary,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    // Token WA Gateway
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Link, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Token WA Gateway", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = tokenWaGateway,
                            onValueChange = { tokenWaGateway = it },
                            placeholder = { Text("Masukkan token Fonnte", color = TextTertiary) },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = if (showToken) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { showToken = !showToken }) {
                                    Icon(
                                        imageVector = if (showToken) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = if (showToken) "Sembunyikan" else "Tampilkan",
                                        tint = TextSecondary
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PinangGreen,
                                unfocusedBorderColor = CardBorder,
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row {
                            Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = TextTertiary, modifier = Modifier.size(14.dp).padding(top = 2.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Token API Fonnte untuk mengirim nota otomatis ke petani.",
                                fontSize = 11.sp,
                                color = TextTertiary,
                                lineHeight = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Fonnte Tutorial Dropdown
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showFonnteTutorial = !showFonnteTutorial }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (showFonnteTutorial) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = PinangGreen,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Cara mendapatkan Token Fonnte",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PinangGreen
                            )
                        }

                        AnimatedVisibility(visible = showFonnteTutorial) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(DividerColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text("1. Buka website fonnte.com dan daftar akun.", fontSize = 11.sp, color = TextSecondary)
                                    Text("2. Masuk ke menu 'Device' di dashboard.", fontSize = 11.sp, color = TextSecondary)
                                    Text("3. Scan QR Code dengan WhatsApp Anda untuk menautkan.", fontSize = 11.sp, color = TextSecondary)
                                    Text("4. Masuk ke menu 'API' lalu salin Token yang tersedia.", fontSize = 11.sp, color = TextSecondary)
                                    Text("5. Tempel Token tersebut ke kolom input di atas.", fontSize = 11.sp, color = TextSecondary)
                                }
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = DividerColor)

                    // Tes Koneksi & Status
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { 
                                if (nomorWaLaporan.isBlank() || tokenWaGateway.isBlank()) {
                                    Toast.makeText(context, "Nomor WA dan Token tidak boleh kosong", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                isTestingConnection = true
                                profileViewModel.testWhatsAppConnection(nomorWaLaporan, tokenWaGateway) { success, msg ->
                                    isTestingConnection = false
                                    isBotActive = success
                                    Toast.makeText(context, msg, if (success) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
                                }
                            },
                            enabled = !isTestingConnection,
                            colors = ButtonDefaults.buttonColors(containerColor = DividerColor),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            if (isTestingConnection) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp), color = PinangGreen, strokeWidth = 2.dp)
                            } else {
                                Icon(imageVector = Icons.Default.Sync, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isTestingConnection) "Mengirim..." else "Tes Koneksi WA", color = TextSecondary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        Box(
                            modifier = Modifier
                                .background(if (isBotActive) IconBgGreen else DividerColor, shape = RoundedCornerShape(16.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(if (isBotActive) PinangGreen else TextTertiary))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isBotActive) "BOT AKTIF" else "TIDAK AKTIF", color = if (isBotActive) PinangGreen else TextTertiary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }



            // SIMPAN PERUBAHAN
            Button(
                onClick = {
                    val updatedProfile = profileState?.copy(
                        namaPemilik = namaPemilik,
                        alamatGudang = alamatGudang,
                        nomorWaLaporan = nomorWaLaporan,
                        tokenWaGateway = tokenWaGateway
                    ) ?: PengepulProfile(
                        namaPemilik = namaPemilik,
                        alamatGudang = alamatGudang,
                        nomorWaLaporan = nomorWaLaporan,
                        tokenWaGateway = tokenWaGateway
                    )

                    profileViewModel.updateProfile(
                        updatedProfile,
                        onSuccess = {
                            Toast.makeText(context, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = {
                            Toast.makeText(context, "Gagal menyimpan: ${it.message}", Toast.LENGTH_LONG).show()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PinangGreen)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("SIMPAN PERUBAHAN", fontWeight = FontWeight.Bold, fontSize = 14.sp, letterSpacing = 1.sp)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
