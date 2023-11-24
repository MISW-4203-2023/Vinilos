package com.team3.vinilos.viewModel

import com.team3.vinilos.R


data class FavoriteArtistUiState(
    val isFavorite: Boolean = false,
    val text: Int =
        if (!isFavorite) {R.string.agregar}
        else {R.string.quitar}
)