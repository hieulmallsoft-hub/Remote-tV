package com.example.remotetv.ui.screen.select

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remotetv.R
import com.example.remotetv.ui.theme.CustomBedIcon
import com.example.remotetv.ui.theme.CustomLivingRoomIcon
import com.example.remotetv.ui.theme.CustomStairsIcon
import com.example.remotetv.ui.theme.CustomWifiIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class RoomType {
    LIVING_ROOM,
    BEDROOM,
    FLOOR_2
}

data class SelectTvItem(
    val id: String,
    val brand: String,
    val brandColor: Color,
    val model: String,
    val room: String,
    val roomType: RoomType,
    val statusText: String,
    val statusColor: Color,
    @DrawableRes val imageRes: Int,
    val requiresPin: Boolean = false,
    val isPrimaryConnected: Boolean = false
)

private val defaultSelectTvList = listOf(
    SelectTvItem(
        id = "1",
        brand = "Samsung",
        brandColor = Color(0xFF2563EB),
        model = "QLED 55\"",
        room = "Phòng khách",
        roomType = RoomType.LIVING_ROOM,
        statusText = "Sẵn sàng kết nối",
        statusColor = Color(0xFF059669),
        imageRes = R.drawable.img_tv_samsung,
        isPrimaryConnected = true
    ),
    SelectTvItem(
        id = "2",
        brand = "LG",
        brandColor = Color(0xFFE11D48),
        model = "OLED C2",
        room = "Phòng ngủ",
        roomType = RoomType.BEDROOM,
        statusText = "Yêu cầu mã PIN",
        statusColor = Color(0xFFD97706),
        imageRes = R.drawable.img_tv_lg,
        requiresPin = true
    ),
    SelectTvItem(
        id = "3",
        brand = "Sony",
        brandColor = Color(0xFF334155),
        model = "Android TV",
        room = "Tầng 2",
        roomType = RoomType.FLOOR_2,
        statusText = "Đã phát hiện",
        statusColor = Color(0xFF2563EB),
        imageRes = R.drawable.img_tv_sony
    )
)

private val PrimaryBlue = Color(0xFF1D61E7)
private val DarkText = Color(0xFF0F172A)
private val GrayText = Color(0xFF64748B)

@Composable
fun SelectTvScreen(
    onTvSelected: (SelectTvItem) -> Unit,
    onNavigateBack: () -> Unit = {}
) {
    var isScanning by remember { mutableStateOf(false) }
    var selectedTvId by remember { mutableStateOf("1") }
    var showPinDialogForTv by remember { mutableStateOf<SelectTvItem?>(null) }
    var pinValue by remember { mutableStateOf("") }
    var connectingTv by remember { mutableStateOf<SelectTvItem?>(null) }
    val scope = rememberCoroutineScope()

    val infiniteTransition = rememberInfiniteTransition(label = "scan_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isScanning) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // PIN Dialog
    showPinDialogForTv?.let { tv ->
        AlertDialog(
            onDismissRequest = {
                showPinDialogForTv = null
                pinValue = ""
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Nhập mã PIN", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            },
            text = {
                Column {
                    Text(
                        text = "Nhập mã PIN 4 số hiển thị trên màn hình ${tv.brand} ${tv.model}:",
                        color = GrayText,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = pinValue,
                        onValueChange = { if (it.length <= 4) pinValue = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        placeholder = { Text("0000") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val selected = showPinDialogForTv
                        showPinDialogForTv = null
                        pinValue = ""
                        selected?.let {
                            connectingTv = it
                            scope.launch {
                                delay(1200)
                                connectingTv = null
                                onTvSelected(it)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Xác nhận")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showPinDialogForTv = null
                    pinValue = ""
                }) {
                    Text("Hủy", color = GrayText)
                }
            }
        )
    }

    // Connecting Progress Dialog
    connectingTv?.let { tv ->
        AlertDialog(
            onDismissRequest = {},
            title = null,
            text = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = PrimaryBlue,
                        modifier = Modifier.size(32.dp),
                        strokeWidth = 3.dp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Đang kết nối tới ${tv.brand} ${tv.model}...",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = DarkText
                    )
                }
            },
            confirmButton = {},
            shape = RoundedCornerShape(16.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Title Header
        Text(
            text = "Chọn TV của bạn",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Thiết bị khả dụng trong cùng mạng Wi-Fi",
            fontSize = 15.sp,
            color = GrayText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hero Banner: Living Room & Smart TV
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_select_tv_hero),
                contentDescription = "Living Room TV Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Device Cards List
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            defaultSelectTvList.forEach { tv ->
                val isSelected = selectedTvId == tv.id

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedTvId = tv.id
                        },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp),
                    border = if (isSelected) {
                        BorderStroke(1.5.dp, PrimaryBlue)
                    } else {
                        BorderStroke(1.dp, Color(0xFFE2E8F0))
                    }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // TV Thumbnail on left
                            Box(
                                modifier = Modifier
                                    .size(width = 86.dp, height = 64.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFF1F5F9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = tv.imageRes),
                                    contentDescription = tv.model,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            // Details
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = tv.brand,
                                        color = tv.brandColor,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    // Wi-Fi signal icon
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        CustomWifiIcon(tint = PrimaryBlue, size = 18.dp)
                                        SignalBars(bars = if (tv.id == "1") 3 else 2)
                                    }
                                }

                                Text(
                                    text = tv.model,
                                    color = DarkText,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                // Room location
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    when (tv.roomType) {
                                        RoomType.LIVING_ROOM -> CustomLivingRoomIcon(tint = GrayText, size = 15.dp)
                                        RoomType.BEDROOM -> CustomBedIcon(tint = GrayText, size = 15.dp)
                                        RoomType.FLOOR_2 -> CustomStairsIcon(tint = GrayText, size = 15.dp)
                                    }
                                    Text(
                                        text = tv.room,
                                        color = GrayText,
                                        fontSize = 13.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                // Status Row and Action Button
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(tv.statusColor)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = tv.statusText,
                                            color = tv.statusColor,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    // Button
                                    if (tv.isPrimaryConnected) {
                                        Button(
                                            onClick = {
                                                connectingTv = tv
                                                scope.launch {
                                                    delay(1200)
                                                    connectingTv = null
                                                    onTvSelected(tv)
                                                }
                                            },
                                            shape = RoundedCornerShape(20.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                            modifier = Modifier.height(36.dp)
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text("Kết nối", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                                Spacer(modifier = Modifier.width(2.dp))
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    } else if (tv.requiresPin) {
                                        OutlinedButton(
                                            onClick = {
                                                showPinDialogForTv = tv
                                            },
                                            shape = RoundedCornerShape(20.dp),
                                            border = BorderStroke(1.dp, PrimaryBlue),
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                            modifier = Modifier.height(36.dp)
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text("Nhập mã PIN", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                                Spacer(modifier = Modifier.width(2.dp))
                                                Icon(
                                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Refresh Scan footer
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        if (!isScanning) {
                            isScanning = true
                            scope.launch {
                                delay(2000)
                                isScanning = false
                            }
                        }
                    }
                )
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier
                    .size(20.dp)
                    .rotate(rotation)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isScanning) "Đang tìm TV..." else "Không thấy TV? Quét lại",
                color = PrimaryBlue,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun SignalBars(bars: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.height(14.dp)
    ) {
        val totalBars = 3
        for (i in 1..totalBars) {
            val barHeight = (4 + i * 3).dp
            val isFilled = i <= bars
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(barHeight)
                    .clip(RoundedCornerShape(1.dp))
                    .background(if (isFilled) Color(0xFF64748B) else Color(0xFFCBD5E1))
            )
        }
    }
}
