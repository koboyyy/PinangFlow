package com.pinangflow.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pinangflow.app.presentation.theme.*

enum class BadgeType {
    AKTIF,
    NON_AKTIF,
    SELESAI,
    PROSES
}

@Composable
fun StatusBadge(
    type: BadgeType,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, text) = when (type) {
        BadgeType.AKTIF -> Triple(ActiveGreenBg, ActiveGreen, "Aktif")
        BadgeType.NON_AKTIF -> Triple(InactiveRedBg, InactiveRed, "Non-aktif")
        BadgeType.SELESAI -> Triple(ActiveGreenBg, ActiveGreen, "Selesai")
        BadgeType.PROSES -> Triple(ProcessingBadgeBg, ProcessingBadgeText, "Proses Pengeringan")
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = "● $text",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
fun LabelBadge(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}
