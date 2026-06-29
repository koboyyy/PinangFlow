package com.pinangflow.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pinangflow.app.presentation.theme.*

@Composable
fun TransactionItem(
    avatarInitials: String,
    avatarColor: Color,
    nama: String,
    detail: String,
    jumlah: String,
    waktu: String,
    isIncome: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(avatarColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = avatarInitials,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = avatarColor
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Name & Detail
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = nama,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = TextPrimary
            )
            Text(
                text = detail,
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 16.sp
            )
        }

        // Amount & Time
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = jumlah,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = if (isIncome) PinangGreen else TextPrimary
            )
            Text(
                text = waktu,
                fontSize = 11.sp,
                color = TextTertiary
            )
        }
    }
}
