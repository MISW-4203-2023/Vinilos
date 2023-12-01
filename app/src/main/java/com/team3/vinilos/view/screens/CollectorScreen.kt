package com.team3.vinilos.view.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team3.vinilos.R
import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.view.fragments.AlbumListItem
import com.team3.vinilos.view.fragments.ArtistListItem
import com.team3.vinilos.view.fragments.CommentCard
import com.team3.vinilos.view.fragments.RoundedLetter
import com.team3.vinilos.view.theme.md_theme_dark_onPrimary
import com.team3.vinilos.view.theme.md_theme_dark_primary
import com.team3.vinilos.viewModel.CollectorUiState

@Composable
fun CollectorScreen(
    state: CollectorUiState,
    retryAction: () -> Unit,
    goToArtist: (id: Long) -> Unit,
    goToAlbum: (id: Long) -> Unit
) {
    when (state) {
        is CollectorUiState.Loading -> Text(text = stringResource(R.string.loading_title))
        is CollectorUiState.Success -> CollectorDetail(
            collector = state.collector,
            goToArtist = goToArtist,
            goToAlbum = goToAlbum
        )

        is CollectorUiState.Error -> ErrorScreen(retryAction = retryAction)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectorDetail(
    collector: Collector,
    goToArtist: (id: Long) -> Unit,
    goToAlbum: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Álbums", "Artistas Favoritos", "Comentarios")
    LazyColumn {
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = collector.name,
                    modifier = modifier.testTag("collector_name")
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    style = MaterialTheme.typography.headlineMedium,

                    )
                Divider(modifier.padding(vertical = 16.dp))
                Row(modifier = modifier.padding(horizontal = 16.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.call24),
                        contentDescription = "Número de teléfono ${collector.telephone}",
                        modifier = modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Column {
                        Text(text = "Teléfono", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = collector.telephone.orEmpty(),
                            modifier = modifier.testTag("collector_telefono"),
                            style = MaterialTheme.typography.titleMedium,
                            )
                    }

                }
                Divider(modifier.padding(vertical = 16.dp))
                Row(
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mail24),
                        contentDescription = "Correo Electrónico ${collector.email}",
                        modifier = modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Column {
                        Text(
                            text = "Correo Electrónico",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = collector.email.orEmpty(),
                            modifier = modifier.testTag("collector_correo"),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
        stickyHeader {
            TabRow(selectedTabIndex = state) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index},
                        text = {
                            Text(
                                text = title,
                                modifier = modifier.testTag("${title}"),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis

                                )
                        }
                    )
                }
            }
        }
        when (state) {
            0 -> items(collector.collectorAlbums.orEmpty()) { albums ->
                albums.album?.let {
                    AlbumListItem(
                        album = it,
                        goToAlbum = { goToAlbum(albums.album.id) },
                        modifier = modifier.testTag("collector_album_name"),
                    )

                }
            }

            1 -> items(collector.favoritePerformers.orEmpty()) { performer ->
                ArtistListItem(artist = performer, goToArtist = goToArtist, modifier = modifier.testTag("collector_artist"),
                )
            }

            2 -> items(collector.comments.orEmpty()) { comment ->
                CommentCard(comment = comment, goToAlbum = goToAlbum, modifier = modifier.testTag("collector_comment"),
                )
            }

            else -> {}
        }
    }
}

