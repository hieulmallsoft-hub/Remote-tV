package com.example.remotetv.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomTvIcon(
    tint: Color = Color(0xFF2563EB),
    size: Dp = 24.dp
) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = 2.dp.toPx()

        // Screen frame
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.08f, h * 0.12f),
            size = Size(w * 0.84f, h * 0.62f),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            style = Stroke(width = strokeWidth)
        )

        // Stand neck
        drawLine(
            color = tint,
            start = Offset(w * 0.5f, h * 0.74f),
            end = Offset(w * 0.5f, h * 0.88f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Stand base
        drawLine(
            color = tint,
            start = Offset(w * 0.32f, h * 0.88f),
            end = Offset(w * 0.68f, h * 0.88f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun CustomWifiIcon(
    tint: Color = Color(0xFF2563EB),
    size: Dp = 18.dp
) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = 1.8.dp.toPx()

        // Center dot
        drawCircle(
            color = tint,
            radius = 1.5.dp.toPx(),
            center = Offset(w * 0.5f, h * 0.82f)
        )

        // Inner arc
        val arc1 = Path().apply {
            moveTo(w * 0.35f, h * 0.62f)
            quadraticTo(w * 0.5f, h * 0.50f, w * 0.65f, h * 0.62f)
        }
        drawPath(arc1, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))

        // Outer arc
        val arc2 = Path().apply {
            moveTo(w * 0.20f, h * 0.42f)
            quadraticTo(w * 0.5f, h * 0.24f, w * 0.80f, h * 0.42f)
        }
        drawPath(arc2, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
    }
}

@Composable
fun CustomLivingRoomIcon(
    tint: Color = Color(0xFF64748B),
    size: Dp = 15.dp
) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = 1.6.dp.toPx()

        // Sofa back
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.2f, h * 0.2f),
            size = Size(w * 0.6f, h * 0.45f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Stroke(width = strokeWidth)
        )

        // Sofa base / seat
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.1f, h * 0.48f),
            size = Size(w * 0.8f, h * 0.32f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Stroke(width = strokeWidth)
        )

        // Legs
        drawLine(
            color = tint,
            start = Offset(w * 0.2f, h * 0.80f),
            end = Offset(w * 0.18f, h * 0.95f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(w * 0.8f, h * 0.80f),
            end = Offset(w * 0.82f, h * 0.95f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun CustomBedIcon(
    tint: Color = Color(0xFF64748B),
    size: Dp = 15.dp
) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = 1.6.dp.toPx()

        // Headboard
        drawLine(
            color = tint,
            start = Offset(w * 0.15f, h * 0.25f),
            end = Offset(w * 0.15f, h * 0.85f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Footboard
        drawLine(
            color = tint,
            start = Offset(w * 0.85f, h * 0.50f),
            end = Offset(w * 0.85f, h * 0.85f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Mattress base
        drawLine(
            color = tint,
            start = Offset(w * 0.15f, h * 0.68f),
            end = Offset(w * 0.85f, h * 0.68f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Pillow
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.22f, h * 0.45f),
            size = Size(w * 0.22f, h * 0.18f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx()),
            style = Stroke(width = strokeWidth)
        )
    }
}

@Composable
fun CustomStairsIcon(
    tint: Color = Color(0xFF64748B),
    size: Dp = 15.dp
) {
    Canvas(modifier = Modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val strokeWidth = 1.6.dp.toPx()

        val path = Path().apply {
            moveTo(w * 0.15f, h * 0.85f)
            lineTo(w * 0.40f, h * 0.85f)
            lineTo(w * 0.40f, h * 0.55f)
            lineTo(w * 0.65f, h * 0.55f)
            lineTo(w * 0.65f, h * 0.25f)
            lineTo(w * 0.88f, h * 0.25f)
        }
        drawPath(path, color = tint, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
    }
}
