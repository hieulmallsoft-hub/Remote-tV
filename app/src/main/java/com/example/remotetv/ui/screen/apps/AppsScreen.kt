package com.example.remotetv.ui.screen.apps

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remotetv.ui.screen.remote.CyberBottomNav

// Color Palette
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
private val NeonBlue = Color(0xFF2E7CF6)
private val NeonCyan = Color(0xFF00E5FF)
private val NeonGreen = Color(0xFF00E676)
private val TextWhite = Color(0xFFFFFFFF)
private val TextMuted = Color(0xFF7E8B9F)

data class TvAppItem(
    val name: String,
    val category: String,
    val brandGradient: Brush,
    val iconVector: ImageVector? = null,
    val textLogo: String? = null,
    val accentColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppsScreen(
    onBack: () -> Unit,
    onRemote: () -> Unit,
    onVoice: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Tất cả") }

    val categories = listOf("Tất cả", "Phim ảnh", "Âm nhạc", "Tiện ích", "Trò chơi")

    val allApps = remember {
        listOf(
            TvAppItem(
                name = "YouTube",
                category = "Phim ảnh",
                brandGradient = Brush.linearGradient(listOf(Color(0xFFFF0033), Color(0xFFCC0000))),
                iconVector = Icons.Rounded.PlayArrow,
                accentColor = Color(0xFFFF0033)
            ),
            TvAppItem(
                name = "Netflix",
                category = "Phim ảnh",
                brandGradient = Brush.linearGradient(listOf(Color(0xFF1F0306), Color(0xFFE50914))),
                textLogo = "N",
                accentColor = Color(0xFFE50914)
            ),
            TvAppItem(
                name = "Prime Video",
                category = "Phim ảnh",
                brandGradient = Brush.linearGradient(listOf(Color(0xFF001E3D), Color(0xFF00A8E1))),
                textLogo = "prime",
                accentColor = Color(0xFF00A8E1)
            ),
            TvAppItem(
                name = "Disney+",
                category = "Phim ảnh",
                brandGradient = Brush.linearGradient(listOf(Color(0xFF0B194F), Color(0xFF1E50FF))),
                textLogo = "D+",
                accentColor = Color(0xFF1E50FF)
            ),
            TvAppItem(
                name = "Spotify",
                category = "Âm nhạc",
                brandGradient = Brush.linearGradient(listOf(Color(0xFF0A3319), Color(0xFF1DB954))),
                iconVector = Icons.Rounded.MusicNote,
                accentColor = Color(0xFF1DB954)
            ),
            TvAppItem(
                name = "Apple TV+",
                category = "Phim ảnh",
                brandGradient = Brush.linearGradient(listOf(Color(0xFF333333), Color(0xFF111111))),
                textLogo = "tv+",
                accentColor = Color(0xFFFFFFFF)
            ),
            TvAppItem(
                name = "Trình duyệt",
                category = "Tiện ích",
                brandGradient = Brush.linearGradient(listOf(Color(0xFF0F3A60), Color(0xFF00B4D8))),
                iconVector = Icons.Rounded.Language,
                accentColor = Color(0xFF00B4D8)
            ),
            TvAppItem(
                name = "Thư viện ảnh",
                category = "Tiện ích",
                brandGradient = Brush.linearGradient(listOf(Color(0xFF6B1170), Color(0xFFB5179E))),
                iconVector = Icons.Rounded.PhotoLibrary,
                accentColor = Color(0xFFB5179E)
            ),
            TvAppItem(
                name = "Cài đặt TV",
                category = "Tiện ích",
                brandGradient = Brush.linearGradient(listOf(Color(0xFF283048), Color(0xFF485563))),
                iconVector = Icons.Rounded.Settings,
                accentColor = Color(0xFF90CAF9)
            ),
            TvAppItem(
                name = "Trò chơi",
                category = "Trò chơi",
                brandGradient = Brush.linearGradient(listOf(Color(0xFF4A00E0), Color(0xFF8E2DE2))),
                iconVector = Icons.Rounded.SportsEsports,
                accentColor = Color(0xFF8E2DE2)
            )
        )
    }

    val filteredApps = allApps.filter { app ->
        val matchesQuery = app.name.contains(query, ignoreCase = true)
        val matchesCategory = (selectedCategory == "Tất cả" || app.category == selectedCategory)
        matchesQuery && matchesCategory
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(10.dp))

            // ================= HEADER =================
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
                        text = "Kho Ứng Dụng TV",
                        color = TextWhite,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Mở và điều khiển ứng dụng tức thì",
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
                        Text("Online", color = NeonGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ================= CONNECTED TV CARD =================
            ConnectedTvBanner()

            Spacer(Modifier.height(16.dp))

            // ================= SEARCH BAR =================
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                placeholder = {
                    Text("Tìm ứng dụng trên TV...", color = TextMuted, fontSize = 14.sp)
                },
                leadingIcon = {
                    Icon(Icons.Rounded.Search, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(22.dp))
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Rounded.Close, contentDescription = "Xóa", tint = TextMuted, modifier = Modifier.size(18.dp))
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(26.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = SurfaceDark,
                    unfocusedContainerColor = SurfaceDark,
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = SurfaceBorder,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                )
            )

            Spacer(Modifier.height(14.dp))

            // ================= CATEGORY FILTER TABS =================
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (isSelected) Brush.linearGradient(listOf(NeonBlue, Color(0xFF1D4ED8)))
                                else Brush.linearGradient(listOf(SurfaceDark, SurfaceDark))
                            )
                            .border(
                                1.dp,
                                if (isSelected) NeonCyan.copy(alpha = 0.6f) else SurfaceBorder,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) TextWhite else TextMuted,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ================= APP GRID =================
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredApps) { app ->
                    ModernAppTile(app = app)
                }
            }

            Spacer(Modifier.height(10.dp))

            // ================= RECENT SECTION =================
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mở gần đây",
                        color = TextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "3 ứng dụng",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RecentAppCard(
                        modifier = Modifier.weight(1f),
                        title = "YouTube",
                        sub = "4K • Video",
                        color = Color(0xFFFF0033),
                        icon = Icons.Rounded.PlayArrow
                    )
                    RecentAppCard(
                        modifier = Modifier.weight(1f),
                        title = "Netflix",
                        sub = "Phim bộ",
                        color = Color(0xFFE50914),
                        textLogo = "N"
                    )
                    RecentAppCard(
                        modifier = Modifier.weight(1f),
                        title = "Spotify",
                        sub = "Nhạc chill",
                        color = Color(0xFF1DB954),
                        icon = Icons.Rounded.MusicNote
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // ================= BOTTOM NAVIGATION =================
            CyberBottomNav(
                currentTab = 2,
                onRemoteClick = onRemote,
                onVoiceClick = onVoice,
                onAppsClick = {},
                onSettingsClick = {}
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ConnectedTvBanner() {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = SurfaceDark,
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TV Screen icon container
            Box(
                modifier = Modifier
                    .size(width = 68.dp, height = 48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF152A55), Color(0xFF0E1A33))
                        )
                    )
                    .border(1.dp, NeonBlue.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Tv,
                    contentDescription = null,
                    tint = NeonCyan,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Samsung QLED 55\"",
                    color = TextWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(6.dp).clip(CircleShape).background(NeonGreen))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Đã kết nối • Phòng khách",
                        color = NeonGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Cast / Signal Badge
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(SurfaceDarker)
                    .border(1.dp, SurfaceBorder, CircleShape)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Cast,
                    contentDescription = null,
                    tint = NeonBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun ModernAppTile(app: TvAppItem) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.90f else 1f, label = "appScale")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {}
            )
    ) {
        // App Icon Box with rich gradient and neon glow
        Box(
            modifier = Modifier
                .size(62.dp)
                .shadow(10.dp, RoundedCornerShape(18.dp), spotColor = app.accentColor)
                .clip(RoundedCornerShape(18.dp))
                .background(app.brandGradient)
                .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (app.iconVector != null) {
                Icon(
                    imageVector = app.iconVector,
                    contentDescription = app.name,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            } else if (app.textLogo != null) {
                Text(
                    text = app.textLogo,
                    color = Color.White,
                    fontSize = if (app.textLogo.length > 2) 16.sp else 26.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-0.5).sp
                )
            }
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = app.name,
            color = TextWhite,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun RecentAppCard(
    modifier: Modifier,
    title: String,
    sub: String,
    color: Color,
    icon: ImageVector? = null,
    textLogo: String? = null
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = SurfaceDark,
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                if (icon != null) {
                    Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                } else if (textLogo != null) {
                    Text(textLogo, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.width(8.dp))

            Column {
                Text(
                    text = title,
                    color = TextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = sub,
                    color = TextMuted,
                    fontSize = 9.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

