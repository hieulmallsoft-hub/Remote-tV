package com.example.remotetv.ui.theme.screen.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onRemoteClick: () -> Unit = {},
    onVoiceClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070B14))
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(
            top = 24.dp,
            bottom = 100.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        item {

            HeaderSection()

        }

        uiState.selectedTv?.let { tv ->

            item {

                SelectedTvCard(
                    device = tv,
                    onClick = onRemoteClick
                )

            }

        }

        item {

            Text(
                text = "Quick scenes",
                color = Color.White,
                fontSize = 22.sp
            )

        }

        item {

            QuickScenesSection()

        }

        item {

            Text(
                text = "Your devices",
                color = Color.White,
                fontSize = 22.sp
            )

        }

        items(uiState.devices) { device ->

            DeviceCard(
                device = device,
                onClick = {
                    viewModel.onDeviceClick(device)
                }
            )

        }
    }
}

@Composable
private fun HeaderSection() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Remote TV", color = Color.White, fontSize = 30.sp)
        Text("Control your televisions", color = Color(0xFF9AA4B2), fontSize = 15.sp)
    }
}

@Composable
private fun SelectedTvCard(device: TvDevice, onClick: () -> Unit) {
    ElevatedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(device.name, style = MaterialTheme.typography.titleLarge)
            Text(device.room, color = Color.Gray)
            Text(if (device.isOnline) "Online" else "Offline", color = if (device.isOnline) Color(0xFF2EBD75) else Color.Gray)
        }
    }
}

@Composable
private fun QuickScenesSection() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
        AssistChip(onClick = {}, label = { Text("Watch TV") })
        AssistChip(onClick = {}, label = { Text("Movie") })
        AssistChip(onClick = {}, label = { Text("Sleep") })
    }
}

@Composable
private fun DeviceCard(device: TvDevice, onClick: () -> Unit) {
    ElevatedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(device.name, style = MaterialTheme.typography.titleMedium)
                Text(device.room, color = Color.Gray)
            }
            Text(if (device.isOnline) "Online" else "Offline", color = if (device.isOnline) Color(0xFF2EBD75) else Color.Gray)
        }
    }
}


