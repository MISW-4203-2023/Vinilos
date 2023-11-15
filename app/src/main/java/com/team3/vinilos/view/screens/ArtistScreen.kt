package com.team3.vinilos.view.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.team3.vinilos.R
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.viewModel.ArtistUiState

@Composable
fun ArtistScreen(state: ArtistUiState, retryAction: () -> Unit) {

    when (state) {
        is ArtistUiState.Loading -> Text(text = stringResource(R.string.loading_title))
        is ArtistUiState.Success -> ArtistDetail(artist = state.artist)
        is ArtistUiState.Error -> Column {
            Text(text = stringResource(R.string.error_title))
            Button(onClick = retryAction) {
                Text(text = stringResource(R.string.error_retry))
            }
        }
    }

}

@Composable
fun ArtistDetail(artist: Artist, modifier: Modifier = Modifier) {
    Text(text = artist.name)
}

