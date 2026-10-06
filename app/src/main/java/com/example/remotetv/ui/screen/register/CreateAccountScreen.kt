package com.example.remotetv.ui.screen.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CreateAccountScreen(
    onCloseClick: () -> Unit,
    onContinueClick: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070B14))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create account",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Sign in to manage your devices.",
            color = Color(0xFF9AA4B2),
            modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
        )

        Button(
            onClick = { onContinueClick("user@example.com") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = onCloseClick) {
                Text("Close")
            }

            OutlinedButton(onClick = onLoginClick) {
                Text("Login")
            }
        }
    }
}
