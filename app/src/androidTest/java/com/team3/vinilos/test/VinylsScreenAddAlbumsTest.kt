package com.team3.vinilos.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.team3.vinilos.R
import com.team3.vinilos.test.fake.FakeNetworkAlbumAddRepository
import com.team3.vinilos.test.fake.FakeNetworkAlbumsRepository
import com.team3.vinilos.test.fake.FakeNetworkArtistsRepository
import com.team3.vinilos.view.screens.VinylsApp
import com.team3.vinilos.view.screens.VinylsAppScreen
import com.team3.vinilos.viewModel.AlbumAddViewModel
import com.team3.vinilos.viewModel.AlbumsUiState
import com.team3.vinilos.viewModel.AlbumsViewModel
import com.team3.vinilos.viewModel.ArtistsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.concurrent.thread

class VinylsScreenAddAlbumsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var albumAddViewModel: AlbumAddViewModel
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
            albumAddViewModel = AlbumAddViewModel(
                albumAddRepository = FakeNetworkAlbumAddRepository()
            )
            artistsViewModel = ArtistsViewModel(
                artistsRepository = FakeNetworkArtistsRepository()
            )
            VinylsApp(
                navController = navController,
                albumsViewModel = albumsViewModel,
                albumAddViewModel = albumAddViewModel,
                artistsViewModel = artistsViewModel
            )
        }
    }

    @Test
    fun albumsScreen_openCreateAlbum() {
        navigateToAlbumScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Albums.name)
        when (albumsViewModel.albumsUiState) {
            is AlbumsUiState.Success -> {
                composeTestRule
                    .onNodeWithTag("agregarAlbum", useUnmergedTree = true)
                    .performClick()
                composeTestRule.onNodeWithTag("nombreAlbum", useUnmergedTree = true).assertExists()
            }

            AlbumsUiState.Loading -> {

            }

            AlbumsUiState.Error -> {

            }
        }
    }

    @Test
    fun albumsScreen_saveAlbum_Error() {
        navigateToAlbumScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Albums.name)
        when (albumsViewModel.albumsUiState) {
            is AlbumsUiState.Success -> {
                composeTestRule
                    .onNodeWithTag("agregarAlbum", useUnmergedTree = true)
                    .performClick()

                composeTestRule.onRoot().performTouchInput  {
                    swipeDown()
                }

                composeTestRule
                    .onNodeWithTag("guardarAlbum", useUnmergedTree = true).assertExists()
                    .performTouchInput {
                        swipeDown()
                    }
                    .performClick()


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