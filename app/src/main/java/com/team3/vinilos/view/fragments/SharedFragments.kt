package com.team3.vinilos.view.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team3.vinilos.R
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.model.models.Comment
import com.team3.vinilos.view.theme.md_theme_dark_onPrimary
import com.team3.vinilos.view.theme.md_theme_dark_primary

@Composable
fun RoundedLetter(letter: String) {
    Box(
        modifier = Modifier
            .size(35.dp)
            .clip(CircleShape)
            .background(md_theme_dark_onPrimary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.uppercase(),
            color = md_theme_dark_primary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ArtistListItem(
    artist: Artist,
    goToArtist: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = { Text(artist.name) },
        trailingContent = {
            IconButton(
                onClick = { goToArtist(artist.id) },
                modifier = modifier.testTag("btn ${artist.name}")
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Ir a artista ${artist.name}"
                )
            }
        },
        leadingContent = {
            RoundedLetter(letter = artist.name.first().toString())
        },
        modifier = modifier.padding(4.dp, 8.dp)
    )
    Divider()
}

@Composable
fun AlbumListItem(album: Album, goToAlbum: (id: Long) -> Unit, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = { Text(album.name) },
        supportingContent = { Text(album.genre ?: "") },
        trailingContent = {
            IconButton(
                onClick = { goToAlbum(album.id) },
                modifier = modifier.testTag("btn ${album.name}")
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Ir a álbum ${album.name}"
                )
            }
        },
        leadingContent = {
            RoundedLetter(letter = album.name.first().toString())
        },
        modifier = modifier.padding(4.dp, 8.dp)
    )
    Divider()
}

@Composable
fun CommentCard(comment: Comment, goToAlbum: (id: Long) -> Unit, modifier: Modifier = Modifier) {
    OutlinedCard(
        onClick = { comment.album?.let { goToAlbum(it.id) } },
        modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()

    ) {
        Column(modifier.padding(16.dp)) {
            comment.album?.let {
                RoundedLetter(letter = it.name.first().toString())

                Text(
                    text = it.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(top = 24.dp)
                )
                Text(
                    text = "Álbum",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = modifier.padding(bottom = 24.dp)
                )
            }
            Row(modifier.padding(bottom = 24.dp)) {
                repeat(comment.rating.toInt()) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.star),
                        contentDescription = "Estrella ${it}",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                repeat(5 - comment.rating.toInt()) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.star),
                        contentDescription = "Estrella ${it}",
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                }
            }
            Text(
                text = comment.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }



        Divider()
    }
}