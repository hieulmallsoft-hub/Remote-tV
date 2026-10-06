package com.example.remotetv.ui.screen.pairing

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

private val DarkBackground = Color(0xFF0D0F14)
private val SurfaceColor = Color(0xFF1A1D24)
private val SurfaceLight = Color(0xFF2A2E39)
private val AccentBlue = Color(0xFF2878F4)
private val TextPrimary = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFF8A93A6)
private val TextGreen = Color(0xFF18A765)

@Composable
fun PairingScreen(onConnected: () -> Unit, onCancel: () -> Unit) {
    var pin by remember { mutableStateOf("4821") }
    
    // Animation triggers
    var startAnimation by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(Modifier.fillMaxSize().background(DarkBackground)) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, top = 40.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            // Header Animation
            AnimatedVisibility(
                visible = startAnimation,
                enter = slideInVertically(initialOffsetY = { -50 }) + fadeIn(tween(500))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Ghép nối TV", color = TextPrimary, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Nhập mã PIN đang hiển thị trên TV\nđể kết nối thiết bị.", color = TextSecondary, fontSize = 16.sp, textAlign = TextAlign.Center)
                }
            }

            Spacer(Modifier.height(30.dp))

            // TV Preview Card Animation
            AnimatedVisibility(
                visible = startAnimation,
                enter = slideInVertically(initialOffsetY = { 100 }, animationSpec = tween(500, delayMillis = 200)) + fadeIn(tween(500, delayMillis = 200))
            ) {
                TvPreview()
            }
            
            Spacer(Modifier.height(20.dp))
            
            AnimatedVisibility(
                visible = startAnimation,
                enter = slideInVertically(initialOffsetY = { 100 }, animationSpec = tween(500, delayMillis = 300)) + fadeIn(tween(500, delayMillis = 300))
            ) {
                DeviceCard()
            }

            Spacer(Modifier.height(30.dp))

            // PIN Entry Area
            AnimatedVisibility(
                visible = startAnimation,
                enter = scaleIn(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow), initialScale = 0.8f) + fadeIn(tween(500, delayMillis = 400))
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(SurfaceColor)
                        .padding(24.dp)
                ) {
                    Text("Nhập mã PIN", color = TextPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(20.dp))
                    
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        pin.forEachIndexed { index, digit ->
                            // Staggered animation for each pin digit
                            var digitVisible by remember { mutableStateOf(false) }
                            LaunchedEffect(startAnimation) {
                                if(startAnimation) {
                                    delay(500L + (index * 150L))
                                    digitVisible = true
                                }
                            }
                            
                            val scale by animateFloatAsState(
                                targetValue = if (digitVisible) 1f else 0.5f,
                                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                                label = "scale"
                            )
                            val alpha by animateFloatAsState(
                                targetValue = if (digitVisible) 1f else 0f,
                                animationSpec = tween(300),
                                label = "alpha"
                            )

                            Box(
                                Modifier
                                    .weight(1f)
                                    .height(72.dp)
                                    .scale(scale)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SurfaceLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = digit.toString(),
                                    color = TextPrimary.copy(alpha = alpha),
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    Text("ⓘ  Hãy kiểm tra mã trên màn hình TV của bạn.", color = TextSecondary, fontSize = 13.sp)
                    
                    Spacer(Modifier.height(32.dp))
                    
                    // Button with subtle pulse effect
                    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                    val pulseScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.03f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "pulseScale"
                    )
                    
                    Button(
                        onClick = onConnected,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .scale(pulseScale),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
                    ) {
                        Text("Kết nối", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    TextButton(onClick = { pin = "8421" }, modifier = Modifier.fillMaxWidth()) {
                        Text("⟳  Quét lại thiết bị", color = AccentBlue, fontSize = 16.sp)
                    }
                    
                    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        HorizontalDivider(Modifier.weight(1f), color = SurfaceLight)
                        Text("  hoặc  ", color = TextSecondary, fontSize = 14.sp)
                        HorizontalDivider(Modifier.weight(1f), color = SurfaceLight)
                    }
                    
                    TextButton(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
                        Text("Hủy", color = TextSecondary, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable private fun TvPreview() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF183D7A), Color(0xFF101A35)))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Mã PIN ghép nối", color = Color.White, fontSize = 18.sp)
            Text("Nhập mã này trên điện thoại của bạn", color = Color(0xFFDDE7FF), fontSize = 13.sp)
            Spacer(Modifier.height(16.dp))
            Text("4   8   2   1", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable private fun DeviceCard() {
    Card(
        Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(80.dp, 60.dp)
                    .background(Color(0xFF173D7A), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("TV", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text("Samsung", color = AccentBlue, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("QLED 55\"", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Đã tìm thấy thiết bị", color = TextGreen, fontSize = 14.sp)
            }
        }
    }
}
