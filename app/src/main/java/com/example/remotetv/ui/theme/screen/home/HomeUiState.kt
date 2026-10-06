package com.example.remotetv.ui.theme.screen.home


data class TvDevice(
    val id: String,
    val name: String,
    val room: String,
    val isOnline: Boolean
)

data class HomeUiState(
    val selectedTv: TvDevice? = null,
    val devices: List<TvDevice> = emptyList(),
    val isLoading: Boolean = false
)