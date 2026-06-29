package com.pinangflow.app.presentation.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pinangflow.app.presentation.theme.PinangGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onNavigateNext: () -> Unit
) {
    // Animasi Scale dan Alpha
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        // Jalankan animasi secara bersamaan
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = OvershootEasing(1.2f) // Sedikit efek memantul
                )
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
        }

        // Tunggu total 2 detik sebelum navigasi
        delay(2000L)
        onNavigateNext()
    }

    // Latar belakang putih sesuai permintaan
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
        ) {
            // "Logo" emoji pohon pinang (sama seperti di halaman Login)
            Text(
                text = "🌴",
                fontSize = 80.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Teks Nama Aplikasi dengan warna PinangGreen
            Text(
                text = "PinangFlow",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = PinangGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sistem Manajemen Pengepul",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}

// Fungsi Easing untuk animasi scale
class OvershootEasing(private val tension: Float = 2.0f) : Easing {
    override fun transform(fraction: Float): Float {
        val t = fraction - 1.0f
        return t * t * ((tension + 1) * t + tension) + 1.0f
    }
}
