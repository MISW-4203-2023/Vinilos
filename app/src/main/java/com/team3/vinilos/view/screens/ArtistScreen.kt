package com.team3.vinilos.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.team3.vinilos.R
import com.team3.vinilos.model.Datasource
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.viewModel.ArtistUiState
import com.team3.vinilos.viewModel.FavoriteArtistUiState
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.reflect.KFunction1

@Composable
fun ArtistScreen(state: ArtistUiState,  stateFavorite: FavoriteArtistUiState ,retryAction: () -> Unit, addFavorite : (artist:Artist) -> Unit) {

    when (state) {
        is ArtistUiState.Loading -> Text(text = stringResource(R.string.loading_title))
        is ArtistUiState.Success -> ArtistDetail(artist = state.artist, isFavorite = stateFavorite.isFavorite, addFavorite = addFavorite, )
        is ArtistUiState.Error -> ErrorScreen(retryAction)
    }

}

@Composable
fun ArtistDetail(artist: Artist, isFavorite: Boolean ,addFavorite : (artist:Artist) -> Unit, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(5.dp)
    ) {
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = artist.name,
                        modifier = modifier.testTag("artist_name"),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            val simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(artist.image)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.image_broken),
                placeholder = painterResource(R.drawable.icono_img),
                contentDescription = artist.name,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F)

            )
            Text(
                text = artist.albums?.firstOrNull()?.name.orEmpty(),
                modifier = Modifier.padding(16.dp, bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = simpleDateFormat.format(artist.birthDate),
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, top = 4.dp),
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = artist.description.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp)
            )
        }

    }
    Box(modifier = Modifier.fillMaxSize()) {
        ExtendedFloatingActionButton(
            onClick = { addFavorite(artist) },
            modifier = Modifier
                .padding(all = 8.dp) // add margin
                .align(alignment = Alignment.CenterEnd),
            icon = { Icon(Icons.Filled.Favorite, "Agregar a favoritos.") },
            text = { Text(text = isFavorite.toString()) },
        )
    }
}

@Preview
@Composable
private fun ArtistPreview() {
}




