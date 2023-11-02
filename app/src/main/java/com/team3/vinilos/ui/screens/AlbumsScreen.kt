package com.team3.vinilos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team3.vinilos.data.Datasource
import com.team3.vinilos.data.models.Album
import com.team3.vinilos.ui.theme.md_theme_dark_onPrimary
import com.team3.vinilos.ui.theme.md_theme_dark_primary
import com.team3.vinilos.ui.viewmodels.AlbumsUiState

@Composable
fun AlbumsScreen(state: AlbumsUiState, retryAction: () -> Unit) {

    when (state) {
        is AlbumsUiState.Loading -> Text(text = "Cargando...") // LoadingScreen(modifier = modifier.fillMaxSize())
        is AlbumsUiState.Success -> AlbumsList(albumList = state.albums)
        is AlbumsUiState.Error -> Column {
            Text(text = "Ha ocurrido un error...")
            Button(onClick = retryAction) {
                Text(text = "Reintentar")
            }
        }  // ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }

}

@Composable
fun AlbumsList(albumList: List<Album>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(albumList) { album ->
            AlbumCard(album = album)
            Divider()
        }
    }
}


@Composable
fun AlbumCard(album: Album, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = { Text(album.name) },
        supportingContent = { Text(album.genre ?: "") },
        trailingContent = {
            Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "Ir a artista")
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
                    text = album.name.first().toString().uppercase(),
                    color = md_theme_dark_primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        modifier = modifier.padding(4.dp, 8.dp)
    )
}


@Preview
@Composable
private fun AlbumsPreview() {
    AlbumsList(Datasource().loadAlbums())
}