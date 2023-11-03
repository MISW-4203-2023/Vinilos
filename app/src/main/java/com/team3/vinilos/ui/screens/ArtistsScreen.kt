package com.team3.vinilos.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ArtistsScreen(state: ArtistsUiState, retryAction: () -> Unit){

    when (state) {
        is ArtistsUiState.Loading -> Text(text = "Cargando...")
        is ArtistsUiState.Success -> ArtistsList(artistList = state.artists)
        is ArtistsUiState.Error -> Column {
            Text(text = "Ha ocurrido un error...")
            Button(onClick = retryAction) {
                Text(text = "Reintentar")
            }
        }
    }

}

@Composable
fun ArtistsList(artistList: List<Artist>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(artistList) { artist ->
            ArtistCard(artist = artist)
            Divider()
        }
    }
}


@Composable
fun ArtistCard(artist: Artist, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = { Text(artist.name) },
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
                    text = artist.name.first().toString().uppercase(),
                    color = md_theme_dark_primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        modifier = modifier.padding(4.dp, 8.dp)
    )
}