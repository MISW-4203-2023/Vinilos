package com.team3.vinilos.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.team3.vinilos.R
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.viewModel.AlbumUiState
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AlbumScreen(state: AlbumUiState, retryAction: () -> Unit) {

    when (state) {
        is AlbumUiState.Loading -> Text(text = stringResource(R.string.loading_title))
        is AlbumUiState.Success -> AlbumDetail(album = state.album)
        is AlbumUiState.Error -> ErrorScreen(retryAction)
    }

}

@Composable
fun AlbumDetail(album: Album, modifier: Modifier = Modifier) {
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
                        text = album.name,
                        modifier = modifier.testTag("album_name"),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = album.genre.orEmpty(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            val simpleDateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())

            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .crossfade(true)
                    .data(album.cover)
                    .build(),
                error = painterResource(R.drawable.image_broken),
                placeholder = painterResource(R.drawable.icono_img),
                contentDescription = stringResource(R.string.imagen_del_album),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F)

            )
            Text(
                text = album.performers?.firstOrNull()?.name.orEmpty(),
                modifier = Modifier
                    .padding(16.dp, bottom = 4.dp, top = 16.dp),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = simpleDateFormat.format(album.releaseDate),
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp, top = 4.dp),
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = album.description.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp )
            )
        }

    }

}
