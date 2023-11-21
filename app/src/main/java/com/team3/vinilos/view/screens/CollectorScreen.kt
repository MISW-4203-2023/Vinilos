package com.team3.vinilos.view.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team3.vinilos.R
import com.team3.vinilos.model.Datasource
import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.viewModel.CollectorUiState

@Composable
fun CollectorScreen(state: CollectorUiState, retryAction: () -> Unit) {
    when (state) {
        is CollectorUiState.Loading -> Text(text = stringResource(R.string.loading_title))
        is CollectorUiState.Success -> CollectorDetail(collector = state.collector)
        is CollectorUiState.Error -> ErrorScreen(retryAction = retryAction)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollectorDetail(collector: Collector, modifier: Modifier = Modifier) {
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
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
                Divider(modifier.padding(vertical = 16.dp))
                Row(modifier = modifier.padding(horizontal = 16.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.dark_mode_24),
                        contentDescription = "Teléfono",
                        modifier = modifier
                            .width(40.dp)
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Column {
                        Text(text = "Teléfono", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = collector.telephone.orEmpty(),
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
                        painter = painterResource(id = R.drawable.dark_mode_24),
                        contentDescription = "Correo Electrónico",
                        modifier = modifier
                            .width(40.dp)
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Column {
                        Text(
                            text = "Correo Electrónico",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = collector.email.orEmpty(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
        stickyHeader{
            TabRow(selectedTabIndex = state) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index },
                        text = {
                            Text(
                                text = title,
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
                Text(text = albums.id.toString())
                Divider()
            }
            1 -> items(collector.favoritePerformers.orEmpty()) { performer ->
                Text(text = performer.name)
                Divider()
            }
            2 -> items(collector.comments.orEmpty()) { comment ->
                Text(text = comment.description)
                Divider()
            }
            else -> {}
        }
    }
}


@Preview
@Composable
private fun CollectorsPreview() {
    CollectorDetail(Datasource().loadCollectors(10)[0])
}