package com.example.remotetv.ui.screen.remote

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Design System Palette - Cyber Obsidian & Neon Glow
private val BgGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF090C15),
        Color(0xFF0F1422),
        Color(0xFF080A11)
    )
)
private val SurfaceDark = Color(0xFF131722)
private val SurfaceDarker = Color(0xFF0C0F17)
private val SurfaceBorder = Color(0xFF262E44)
private val SurfaceGlowBorder = Color(0xFF384566)
private val NeonBlue = Color(0xFF2E7CF6)
private val NeonCyan = Color(0xFF00E5FF)
private val NeonRed = Color(0xFFFF334B)
private val NeonGreen = Color(0xFF00E676)
private val TextWhite = Color(0xFFFFFFFF)
private val TextMuted = Color(0xFF7E8B9F)
private val TextSubtle = Color(0xFF4C566A)

@Composable
fun ConnectedScreen(onStart: () -> Unit, onOtherTv: () -> Unit) {
    Box(Modifier.fillMaxSize().background(Color(0xFFF7FAFF))) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, bottom = 108.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(30.dp))
            Text("Kết nối thành công", color = Color(0xFF10131A), fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("TV của bạn đã sẵn sàng để điều khiển.", color = Color(0xFF65718A), fontSize = 16.sp, textAlign = TextAlign.Center)
            Spacer(Modifier.height(24.dp))
            TvScene(Modifier.fillMaxWidth().height(260.dp))
            Spacer(Modifier.height(20.dp))
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(22.dp)) {
                Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(56.dp).clip(CircleShape).background(Color(0xFFDDF8EE)), contentAlignment = Alignment.Center) {
                        Text("✓", color = Color(0xFF18A765), fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text("Đã kết nối với", color = Color(0xFF18A765), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                        Text("Samsung QLED 55\"", color = Color(0xFF10131A), fontSize = 19.sp, fontWeight = FontWeight.Bold)
                        Text("Phòng khách • 5GHz Wi-Fi", color = Color(0xFF65718A), fontSize = 14.sp)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(22.dp)) {
                Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFEBF3FE)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Rounded.Wifi, null, tint = NeonBlue, modifier = Modifier.size(24.dp))
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Tín hiệu xuất sắc", color = Color(0xFF10131A), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("Độ trễ cực thấp (< 5ms)", color = Color(0xFF65718A), fontSize = 13.sp)
                    }
                    Box(Modifier.clip(RoundedCornerShape(6.dp)).background(Color(0xFFDDF8EE)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Text("Ổn định", color = Color(0xFF18A765), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            TextButton(onClick = onOtherTv) {
                Text("Kết nối TV khác", color = NeonBlue, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
        Button(
            onClick = onStart,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 18.dp)
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NeonBlue)
        ) {
            Text("Bắt đầu điều khiển  →", fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TvScene(modifier: Modifier) {
    Box(
        modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF0F2042), Color(0xFF1A3870), Color(0xFF2A5298))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Rounded.Tv, null, tint = Color.White.copy(alpha = 0.9f), modifier = Modifier.size(54.dp))
            Spacer(Modifier.height(8.dp))
            Text("SAMSUNG SMART TV", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Text("4K Ultra HD • HDR10+", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        }
    }
}

@Composable
fun RemoteScreen(
    onVoiceClick: () -> Unit = {},
    onAppsClick: () -> Unit = {}
) {
    var isPowerOn by remember { mutableStateOf(true) }
    var currentVolume by remember { mutableStateOf(18) }
    var currentChannel by remember { mutableStateOf(5) }

    // Pulsing animation for Mic glow
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 22.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))

            // ================= TOP BAR =================
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Power Button with glowing indicator
                CyberIconButton(
                    icon = Icons.Rounded.PowerSettingsNew,
                    iconTint = if (isPowerOn) NeonRed else TextMuted,
                    borderColor = if (isPowerOn) NeonRed.copy(alpha = 0.5f) else SurfaceBorder,
                    glowColor = if (isPowerOn) NeonRed.copy(alpha = 0.25f) else Color.Transparent,
                    onClick = { isPowerOn = !isPowerOn }
                )

                // TV Status Pill Badge
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = SurfaceDark,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (isPowerOn) NeonGreen else TextSubtle)
                        )
                        Spacer(Modifier.width(8.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = "Samsung QLED 55\"",
                                color = TextWhite,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isPowerOn) "Đã kết nối • Phòng khách" else "Đã tắt nguồn",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                // Input Source / Settings button
                CyberIconButton(
                    icon = Icons.Rounded.Input,
                    iconTint = NeonCyan,
                    borderColor = SurfaceBorder,
                    onClick = {}
                )
            }

            Spacer(Modifier.height(26.dp))

            // ================= FUTURISTIC D-PAD WHEEL =================
            CyberDpad(
                onUp = {},
                onDown = {},
                onLeft = {},
                onRight = {},
                onOk = {}
            )

            Spacer(Modifier.height(26.dp))

            // ================= QUICK ACTION ROW =================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CyberActionButton(icon = Icons.Rounded.ArrowBack, label = "Quay lại", onClick = {})
                CyberActionButton(icon = Icons.Rounded.Home, label = "Trang chủ", onClick = {})
                CyberActionButton(icon = Icons.Rounded.GridView, label = "Ứng dụng", onClick = onAppsClick, highlight = true)
                CyberActionButton(icon = Icons.Rounded.VolumeOff, label = "Tắt tiếng", onClick = {})
            }

            Spacer(Modifier.height(28.dp))

            // ================= ROCKERS & VOICE ASSISTANT =================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // VOL ROCKER
                CyberRocker(
                    label = "VOL",
                    valueText = "$currentVolume",
                    upIcon = Icons.Rounded.Add,
                    downIcon = Icons.Rounded.Remove,
                    onUp = { currentVolume = (currentVolume + 1).coerceAtMost(100) },
                    onDown = { currentVolume = (currentVolume - 1).coerceAtLeast(0) }
                )

                // CENTER PULSING VOICE ASSISTANT ORB
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        NeonCyan.copy(alpha = 0.35f),
                                        NeonBlue.copy(alpha = 0.15f),
                                        Color.Transparent
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8), Color(0xFF0F1E48))
                                    )
                                )
                                .border(1.5.dp, NeonCyan.copy(alpha = 0.8f), CircleShape)
                                .shadow(12.dp, CircleShape, spotColor = NeonCyan)
                                .clickable { onVoiceClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Mic,
                                contentDescription = "Giọng nói",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Giọng nói",
                        color = NeonCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // CH ROCKER
                CyberRocker(
                    label = "CH",
                    valueText = "0$currentChannel",
                    upIcon = Icons.Rounded.KeyboardArrowUp,
                    downIcon = Icons.Rounded.KeyboardArrowDown,
                    onUp = { currentChannel = (currentChannel + 1).coerceAtMost(99) },
                    onDown = { currentChannel = (currentChannel - 1).coerceAtLeast(1) }
                )
            }

            Spacer(Modifier.height(30.dp))

            // ================= BOTTOM FLOATING GLASS NAVIGATION =================
            CyberBottomNav(
                currentTab = 0,
                onRemoteClick = {},
                onVoiceClick = onVoiceClick,
                onAppsClick = onAppsClick,
                onSettingsClick = {}
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ------------------- COMPONENT HELPERS -------------------

@Composable
private fun CyberIconButton(
    icon: ImageVector,
    iconTint: Color,
    borderColor: Color,
    glowColor: Color = Color.Transparent,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(SurfaceDark)
            .border(1.dp, borderColor, CircleShape)
            .drawBehind {
                if (glowColor != Color.Transparent) {
                    drawCircle(glowColor, radius = size.minDimension * 0.7f)
                }
            }
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
    }
}

@Composable
private fun CyberDpad(
    onUp: () -> Unit,
    onDown: () -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit,
    onOk: () -> Unit
) {
    val dpadSize = 250.dp

    Box(
        modifier = Modifier
            .size(dpadSize)
            .shadow(24.dp, CircleShape, spotColor = NeonBlue.copy(alpha = 0.4f))
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(SurfaceDark, SurfaceDarker, Color(0xFF07090F))
                )
            )
            .border(2.dp, Brush.linearGradient(listOf(SurfaceGlowBorder, SurfaceBorder, NeonBlue.copy(alpha = 0.3f))), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Outer decorative ring line
        Box(
            modifier = Modifier
                .size(dpadSize - 16.dp)
                .clip(CircleShape)
                .border(1.dp, Color(0xFF1E2536), CircleShape)
        )

        // UP BUTTON
        DpadDirectionButton(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
                .size(width = 90.dp, height = 55.dp),
            icon = Icons.Rounded.KeyboardArrowUp,
            onClick = onUp
        )

        // DOWN BUTTON
        DpadDirectionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
                .size(width = 90.dp, height = 55.dp),
            icon = Icons.Rounded.KeyboardArrowDown,
            onClick = onDown
        )

        // LEFT BUTTON
        DpadDirectionButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
                .size(width = 55.dp, height = 90.dp),
            icon = Icons.Rounded.KeyboardArrowLeft,
            onClick = onLeft
        )

        // RIGHT BUTTON
        DpadDirectionButton(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
                .size(width = 55.dp, height = 90.dp),
            icon = Icons.Rounded.KeyboardArrowRight,
            onClick = onRight
        )

        // CENTER "OK" BUTTON
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val okScale by animateFloatAsState(if (isPressed) 0.93f else 1f, label = "okScale")

        Box(
            modifier = Modifier
                .size(100.dp)
                .scale(okScale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF242C40),
                            Color(0xFF151A27),
                            Color(0xFF0F131E)
                        )
                    )
                )
                .border(1.5.dp, Brush.linearGradient(listOf(NeonCyan.copy(alpha = 0.7f), NeonBlue.copy(alpha = 0.5f))), CircleShape)
                .shadow(10.dp, CircleShape, spotColor = NeonCyan)
                .clickable(interactionSource = interactionSource, indication = null, onClick = onOk),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "OK",
                color = TextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
private fun DpadDirectionButton(
    modifier: Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (isPressed) NeonBlue.copy(alpha = 0.2f) else Color.Transparent)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isPressed) NeonCyan else TextWhite.copy(alpha = 0.85f),
            modifier = Modifier.size(34.dp)
        )
    }
}

@Composable
private fun CyberActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    highlight: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.92f else 1f, label = "btnScale")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .scale(scale)
                .clip(CircleShape)
                .background(
                    if (highlight) Brush.linearGradient(listOf(Color(0xFF1E2F52), Color(0xFF15203B)))
                    else Brush.linearGradient(listOf(SurfaceDark, SurfaceDarker))
                )
                .border(
                    1.dp,
                    if (highlight) NeonBlue.copy(alpha = 0.6f) else SurfaceBorder,
                    CircleShape
                )
                .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (highlight) NeonCyan else TextWhite,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = label,
            color = if (highlight) NeonCyan else TextMuted,
            fontSize = 12.sp,
            fontWeight = if (highlight) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
private fun CyberRocker(
    label: String,
    valueText: String,
    upIcon: ImageVector,
    downIcon: ImageVector,
    onUp: () -> Unit,
    onDown: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(66.dp)
            .height(172.dp),
        shape = RoundedCornerShape(33.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // UP BUTTON
            IconButton(
                onClick = onUp,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Icon(upIcon, contentDescription = null, tint = TextWhite, modifier = Modifier.size(26.dp))
            }

            // CENTER BADGE
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = label,
                    color = NeonBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = valueText,
                    color = TextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // DOWN BUTTON
            IconButton(
                onClick = onDown,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Icon(downIcon, contentDescription = null, tint = TextWhite, modifier = Modifier.size(26.dp))
            }
        }
    }
}

@Composable
fun CyberBottomNav(
    currentTab: Int,
    onRemoteClick: () -> Unit,
    onVoiceClick: () -> Unit,
    onAppsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(26.dp),
        color = SurfaceDark,
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .shadow(16.dp, RoundedCornerShape(26.dp), spotColor = Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CyberNavItem(
                icon = Icons.Rounded.Tv,
                label = "Remote",
                isSelected = currentTab == 0,
                onClick = onRemoteClick
            )
            CyberNavItem(
                icon = Icons.Rounded.Mic,
                label = "Giọng nói",
                isSelected = currentTab == 1,
                onClick = onVoiceClick
            )
            CyberNavItem(
                icon = Icons.Rounded.GridView,
                label = "Ứng dụng",
                isSelected = currentTab == 2,
                onClick = onAppsClick
            )
            CyberNavItem(
                icon = Icons.Rounded.Settings,
                label = "Cài đặt",
                isSelected = currentTab == 3,
                onClick = onSettingsClick
            )
        }
    }
}

@Composable
private fun CyberNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(if (isSelected) NeonBlue.copy(alpha = 0.16f) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) NeonCyan else TextMuted,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text = label,
                color = if (isSelected) NeonCyan else TextMuted,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

