package com.team3.vinilos.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.team3.vinilos.test.fake.FakeNetworkAlbumRepository
import com.team3.vinilos.test.fake.FakeNetworkAlbumsRepository
import com.team3.vinilos.test.fake.FakeNetworkArtistsRepository
import com.team3.vinilos.view.screens.VinylsApp
import com.team3.vinilos.view.screens.VinylsAppScreen
import com.team3.vinilos.viewModel.AlbumViewModel
import com.team3.vinilos.viewModel.AlbumsUiState
import com.team3.vinilos.viewModel.AlbumsViewModel
import com.team3.vinilos.viewModel.ArtistsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class VinylsScreenAlbumDetailTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var albumViewModel: AlbumViewModel
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
            albumViewModel = AlbumViewModel(
                albumRepository = FakeNetworkAlbumRepository()
            )
            artistsViewModel = ArtistsViewModel(
                artistsRepository = FakeNetworkArtistsRepository()
            )
            VinylsApp(
                navController = navController,
                albumsViewModel = albumsViewModel,
                artistsViewModel = artistsViewModel,
                albumViewModel = albumViewModel
            )
        }
    }

    @Test
    fun albumDetailScreen_clickOn30Details_validateTitle() {
        navigateToAlbumScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Albums.name)
        when (albumsViewModel.albumsUiState) {
            is AlbumsUiState.Success -> {
                val albums = (albumsViewModel.albumsUiState as AlbumsUiState.Success).albums

                for (j in 0 until 30) {
                    val i = Random.nextInt(1, 90)
                    val albumName = albums[i].name
                    composeTestRule.onNodeWithTag("albums_list")
                        .performScrollToIndex(i)
                    composeTestRule.onNodeWithTag("btn $albumName")
                        .performClick()
                    navController.assertCurrentRouteName("${VinylsAppScreen.Albums.name}/{albumId}")

                    composeTestRule.onNodeWithTag("album_name")
                        .assertTextEquals(albumName)
                    composeTestRule.onNodeWithTag("back_button")
                        .performClick()
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