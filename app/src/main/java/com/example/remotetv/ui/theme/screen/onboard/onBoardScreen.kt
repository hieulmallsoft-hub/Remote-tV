package com.example.remotetv.ui.theme.screen.onboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun OnBoardScreen(
    onNavigateNext: () -> Unit
){
    LaunchedEffect(Unit) {
        delay(2500)

        onNavigateNext()
    }


}