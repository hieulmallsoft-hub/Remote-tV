package com.example.remotetv.ui.screen.voice

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

private val BgGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF090C15),
        Color(0xFF0F1422),
        Color(0xFF080A11)
    )
)
private val SurfaceDark = Color(0xFF131722)
private val SurfaceBorder = Color(0xFF262E44)
private val NeonBlue = Color(0xFF2E7CF6)
private val NeonCyan = Color(0xFF00E5FF)
private val NeonGreen = Color(0xFF00E676)
private val TextWhite = Color(0xFFFFFFFF)
private val TextMuted = Color(0xFF7E8B9F)

@Composable
fun VoiceControlScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var listening by remember { mutableStateOf(false) }
    var heard by remember { mutableStateOf("Nhấn vào Mic để bắt đầu nói") }
    var recognizer by remember { mutableStateOf<SpeechRecognizer?>(null) }

    val startListening = {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            recognizer?.startListening(
                Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN")
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                }
            )
            listening = true
        }
    }

    val permission = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) startListening()
    }

    DisposableEffect(Unit) {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            recognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
                setRecognitionListener(object : RecognitionListener {
                    override fun onResults(results: android.os.Bundle) {
                        heard = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull() ?: "Không nghe rõ"
                        listening = false
                    }
                    override fun onError(error: Int) {
                        heard = "Không nhận được giọng nói (mã lỗi $error)"
                        listening = false
                    }
                    override fun onBeginningOfSpeech() {
                        heard = "Đang lắng nghe bạn nói..."
                        listening = true
                    }
                    override fun onEndOfSpeech() {
                        listening = false
                    }
                    override fun onReadyForSpeech(params: android.os.Bundle?) {
                        heard = "Hãy nói lệnh của bạn ngay..."
                    }
                    override fun onRmsChanged(rmsdB: Float) {}
                    override fun onBufferReceived(buffer: ByteArray?) {}
                    override fun onPartialResults(partialResults: android.os.Bundle?) {
                        heard = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.firstOrNull() ?: heard
                    }
                    override fun onEvent(eventType: Int, params: android.os.Bundle?) {}
                })
            }
        } else {
            heard = "Thiết bị chưa hỗ trợ nhận dạng giọng nói"
        }
        onDispose { recognizer?.destroy() }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (listening) 1.15f else 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (listening) 700 else 1400, easing = FastOutSlowInEasing),
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
                .padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(SurfaceDark)
                        .border(1.dp, SurfaceBorder, CircleShape)
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "Quay lại",
                        tint = TextWhite,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = "Trợ Lý Giọng Nói",
                        color = TextWhite,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Điều khiển TV bằng tiếng Việt",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(NeonBlue.copy(alpha = 0.15f))
                        .border(1.dp, NeonBlue.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(6.dp).clip(CircleShape).background(NeonGreen))
                        Spacer(Modifier.width(6.dp))
                        Text("Samsung QLED", color = NeonGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(48.dp))

            // Big Glowing Mic Orb
            Box(
                modifier = Modifier
                    .size(210.dp)
                    .scale(pulseScale),
                contentAlignment = Alignment.Center
            ) {
                // Outer glow aura
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    if (listening) NeonCyan.copy(alpha = 0.45f) else NeonBlue.copy(alpha = 0.25f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // Main Mic Button
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .shadow(24.dp, CircleShape, spotColor = if (listening) NeonCyan else NeonBlue)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = if (listening) listOf(NeonCyan, Color(0xFF0091EA), Color(0xFF0D47A1))
                                else listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8), Color(0xFF0F1E48))
                            )
                        )
                        .border(2.dp, if (listening) NeonCyan else NeonBlue.copy(alpha = 0.6f), CircleShape)
                        .clickable {
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                                startListening()
                            } else {
                                permission.launch(Manifest.permission.RECORD_AUDIO)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Mic,
                        contentDescription = "Mic",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            Text(
                text = if (listening) "Đang lắng nghe..." else "Sẵn sàng nhận lệnh",
                color = if (listening) NeonCyan else TextWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = heard,
                color = TextMuted,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(32.dp))

            // Suggested Voice Commands
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = SurfaceDark,
                border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = "💡 Câu lệnh mẫu:",
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(10.dp))
                    listOf(
                        "\"Mở Netflix trên TV\"",
                        "\"Tăng âm lượng lên 25\"",
                        "\"Tìm kiếm nhạc Sơn Tùng trên YouTube\"",
                        "\"Chuyển sang cổng HDMI 1\""
                    ).forEach { sample ->
                        Text(
                            text = "•  $sample",
                            color = NeonCyan.copy(alpha = 0.85f),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            // Cancel button
            OutlinedButton(
                onClick = {
                    recognizer?.stopListening()
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextMuted)
            ) {
                Text("Quay lại Remote", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

