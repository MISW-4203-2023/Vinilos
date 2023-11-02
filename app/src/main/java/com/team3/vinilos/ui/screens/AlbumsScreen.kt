package com.team3.vinilos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team3.vinilos.data.Datasource
import com.team3.vinilos.models.Album

@Composable
fun AlbumsScreen() {
    AlbumsList(albumList =  Datasource().loadAlbums())
}
@Composable
fun AlbumsList(albumList: List<Album>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(albumList) {album ->
            AlbumCard(album = album)
        }
    }
}


@Composable
fun AlbumCard(album: Album, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = { Text(album.name) },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = album.name.first().toString().toUpperCase(),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    )
}



@Preview
@Composable
private fun AlbumsPreview() {
    AlbumsList(Datasource().loadAlbums())
}