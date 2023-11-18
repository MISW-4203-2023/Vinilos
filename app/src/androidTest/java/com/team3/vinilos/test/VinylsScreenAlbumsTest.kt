package com.team3.vinilos.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.team3.vinilos.test.fake.FakeNetworkAlbumsRepository
import com.team3.vinilos.test.fake.FakeNetworkArtistsRepository
import com.team3.vinilos.view.screens.VinylsApp
import com.team3.vinilos.view.screens.VinylsAppScreen
import com.team3.vinilos.viewModel.AlbumsUiState
import com.team3.vinilos.viewModel.AlbumsViewModel
import com.team3.vinilos.viewModel.ArtistsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VinylsScreenAlbumsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var artistsViewModel: ArtistsViewModel

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            albumsViewModel = AlbumsViewModel(
                albumsRepository = FakeNetworkAlbumsRepository()
            )
            artistsViewModel = ArtistsViewModel(
                artistsRepository = FakeNetworkArtistsRepository()
            )
            VinylsApp(
                navController = navController,
                albumsViewModel = albumsViewModel,
                artistsViewModel = artistsViewModel
            )
        }
    }

    @Test
    fun albumsScreen_scrollOverAllList_findAllAlbums() {
        navigateToAlbumScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Albums.name)
        when (albumsViewModel.albumsUiState) {
            is AlbumsUiState.Success -> {
                val albums = (albumsViewModel.albumsUiState as AlbumsUiState.Success).albums

                for (i in 0 until albums.count()) {
                    composeTestRule.onNodeWithTag("albums_list")
                        .performScrollToIndex(i)
                    composeTestRule.onNodeWithText(albums[i].name).assertExists()
                }

            }

            AlbumsUiState.Loading -> {

            }

            AlbumsUiState.Error -> {

            }
        }
    }

    private fun navigateToAlbumScreen() {
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.albums_title)
            .performClick()
    }
}