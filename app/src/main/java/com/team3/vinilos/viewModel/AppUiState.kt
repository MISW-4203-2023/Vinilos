package com.team3.vinilos.viewModel

import com.team3.vinilos.R

data class AppUiState(
    val isDarkMode: Boolean = true,
    val toggleContentDescription: Int =
        if (isDarkMode) R.string.deactivate_dark_mode_message
        else R.string.activate_dark_mode_message,
    val toggleIcon: Int =
        if (isDarkMode) R.drawable.light_mode_24 else R.drawable.dark_mode_24
)