package com.team3.vinilos.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team3.vinilos.R
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.view.fragments.ArtistListItem
import com.team3.vinilos.view.theme.md_theme_dark_onPrimary
import com.team3.vinilos.view.theme.md_theme_dark_primary
import com.team3.vinilos.viewModel.ArtistsUiState

@Composable
fun ArtistsScreen(state: ArtistsUiState, retryAction: () -> Unit, goToDetail: (id: Long) -> Unit) {

    when (state) {
        is ArtistsUiState.Loading -> LoadingScreen()
        is ArtistsUiState.Success -> ArtistsList(
            artistList = state.artists,
            goToDetail = goToDetail
        )

        is ArtistsUiState.Error -> ErrorScreen(retryAction)
    }

}

@Composable
fun ArtistsList(
    artistList: List<Artist>,
    goToDetail: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.testTag("artist_list")) {
        items(artistList) { artist ->
            ArtistCard(artist = artist, goToDetail = goToDetail)
            Divider()
        }
    }
}


@Composable
fun ArtistCard(artist: Artist, goToDetail : (id:Long) ->  Unit,  modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = { Text(artist.name) },
        trailingContent = {
            IconButton(onClick = { goToDetail(artist.id) }, modifier = modifier.testTag("btn ${artist.name}")) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,contentDescription = "Ir a artista ${artist.name}"
                )
            }
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(md_theme_dark_onPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = artist.name.first().uppercase(),
                    color = md_theme_dark_primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        modifier = modifier.padding(4.dp, 8.dp)
    )
}