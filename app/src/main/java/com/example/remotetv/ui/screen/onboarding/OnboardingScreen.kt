package com.example.remotetv.ui.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remotetv.R
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int,
    val buttonText: String
)

private val onboardingPages = listOf(
    OnboardingPageData(
        title = "Điều khiển TV\nbằng giọng nói",
        description = "Chỉ cần nói, TV sẽ nghe theo.\nĐơn giản hơn bao giờ hết!",
        imageRes = R.drawable.img_onboarding_1,
        buttonText = "Tiếp theo"
    ),
    OnboardingPageData(
        title = "Ra lệnh tự nhiên\nnhư trò chuyện",
        description = "Chuyển kênh, tăng giảm âm lượng,\nmở ứng dụng... tất cả chỉ với giọng nói\ncủa bạn.",
        imageRes = R.drawable.img_onboarding_2,
        buttonText = "Tiếp theo"
    ),
    OnboardingPageData(
        title = "Trải nghiệm\nthông minh hơn",
        description = "Biến giọng nói của bạn thành chiếc\nđiều khiển mạnh mẽ, tiện lợi và hiện đại\nngay trên điện thoại.",
        imageRes = R.drawable.img_onboarding_3,
        buttonText = "Bắt đầu sử dụng"
    )
)

private val PrimaryBlue = Color(0xFF1D61E7)
private val DarkText = Color(0xFF0F172A)
private val GrayText = Color(0xFF64748B)
private val SkipText = Color(0xFF475569)
private val DotInactive = Color(0xFFD6E4FF)

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar with Skip Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = currentPage < onboardingPages.size - 1,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(
                    text = "Bỏ qua",
                    color = SkipText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onFinish
                        )
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Titles & Subtitles
        val currentPageData = onboardingPages[currentPage]
        Text(
            text = currentPageData.title,
            color = DarkText,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = currentPageData.description,
            color = GrayText,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pager with Illustrations
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { pageIndex ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = onboardingPages[pageIndex].imageRes),
                    contentDescription = onboardingPages[pageIndex].title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Page Indicator Dots
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(onboardingPages.size) { index ->
                val isSelected = index == currentPage
                val dotWidth by animateDpAsState(
                    targetValue = if (isSelected) 22.dp else 8.dp,
                    animationSpec = tween(durationMillis = 300),
                    label = "dotWidth"
                )
                val dotColor by animateColorAsState(
                    targetValue = if (isSelected) PrimaryBlue else DotInactive,
                    animationSpec = tween(durationMillis = 300),
                    label = "dotColor"
                )

                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(dotWidth)
                        .clip(CircleShape)
                        .background(dotColor)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Action Button
        Button(
            onClick = {
                if (currentPage < onboardingPages.size - 1) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(currentPage + 1)
                    }
                } else {
                    onFinish()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 4.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currentPageData.buttonText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
