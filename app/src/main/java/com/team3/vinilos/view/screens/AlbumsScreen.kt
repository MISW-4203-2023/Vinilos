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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team3.vinilos.R
import com.team3.vinilos.model.Datasource
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.view.fragments.AlbumListItem
import com.team3.vinilos.view.theme.md_theme_dark_onPrimary
import com.team3.vinilos.view.theme.md_theme_dark_primary
import com.team3.vinilos.viewModel.AlbumsUiState

@Composable
fun AlbumsScreen(state: AlbumsUiState, retryAction: () -> Unit, goToDetail: (id: Long) -> Unit) {

    when (state) {
        is AlbumsUiState.Loading -> LoadingScreen()
        is AlbumsUiState.Success -> AlbumsList(albumList = state.albums, goToDetail = goToDetail)
        is AlbumsUiState.Error -> ErrorScreen(retryAction)
    }

}

@Composable
fun AlbumsList(
    albumList: List<Album>,
    goToDetail: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.testTag("albums_list")) {
        items(albumList) { album ->
            AlbumListItem(album = album, goToAlbum = { goToDetail(album.id) })
        }
    }
}

@Preview
@Composable
private fun AlbumsPreview() {
    AlbumsList(Datasource().loadAlbums(), goToDetail = {})
}