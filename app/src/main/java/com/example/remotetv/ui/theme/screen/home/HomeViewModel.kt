package com.example.remotetv.ui.theme.screen.home


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(
            selectedTv = TvDevice(
                id = "1",
                name = "Living Room TV",
                room = "Living room",
                isOnline = true
            ),
            devices = listOf(
                TvDevice(
                    id = "1",
                    name = "Living Room TV",
                    room = "Living room",
                    isOnline = true
                ),
                TvDevice(
                    id = "2",
                    name = "Bedroom TV",
                    room = "Bedroom",
                    isOnline = true
                ),
                TvDevice(
                    id = "3",
                    name = "Guest Room TV",
                    room = "Guest room",
                    isOnline = false
                )
            )
        )
    )

    val uiState = _uiState.asStateFlow()

    fun onDeviceClick(device: TvDevice) {
        _uiState.value = _uiState.value.copy(
            selectedTv = device
        )
    }
}