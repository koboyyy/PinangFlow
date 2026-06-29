package com.pinangflow.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pinangflow.app.domain.model.Batch
import com.pinangflow.app.domain.model.BatchStatus
import com.pinangflow.app.presentation.theme.*

@Composable
fun BatchCard(
    batch: Batch,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Batch number + Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Batch #${batch.nomorBatch}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextPrimary
                )
                StatusBadge(
                    type = if (batch.status == BatchStatus.SELESAI) BadgeType.SELESAI else BadgeType.PROSES
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Date info
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                val dateText = if (batch.tanggalSelesai != null) {
                    "Masuk: ${batch.tanggalMasuk} | Selesai: ${batch.tanggalSelesai}"
                } else {
                    "Masuk: ${batch.tanggalMasuk}"
                }
                Text(
                    text = dateText,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Weight info
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        Text(
                            text = "Basah",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = formatWeight(batch.beratBasah),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = " Kg",
                                fontSize = 13.sp,
                                color = TextSecondary,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                    }

                    // Arrow / separator
                    Text(
                        text = "→",
                        fontSize = 18.sp,
                        color = TextTertiary,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    Column {
                        Text(
                            text = "Kering",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = formatWeight(batch.beratKering),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = PinangGreen
                            )
                            Text(
                                text = " Kg",
                                fontSize = 13.sp,
                                color = TextSecondary,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Shrinkage badge + chevron
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(ShrinkageBadgeBg)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "↘ Susut: ${String.format("%.0f", batch.persentaseSusut)}% (${formatWeight(batch.kehilanganBerat)} Kg)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = ShrinkageBadgeText
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Lihat detail",
                    tint = PinangGreen,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

private fun formatWeight(weight: Double): String {
    return if (weight == weight.toLong().toDouble()) {
        String.format("%,.0f", weight)
    } else {
        String.format("%,.0f", weight)
    }
}
