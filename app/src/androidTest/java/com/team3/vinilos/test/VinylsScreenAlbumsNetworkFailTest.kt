package com.team3.vinilos.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.team3.vinilos.test.fake.FakeFailNetworkAlbumsRepository
import com.team3.vinilos.test.fake.FakeFailNetworkArtistRepository
import com.team3.vinilos.view.screens.VinylsApp
import com.team3.vinilos.view.screens.VinylsAppScreen
import com.team3.vinilos.viewModel.AlbumsUiState
import com.team3.vinilos.viewModel.AlbumsViewModel
import com.team3.vinilos.viewModel.ArtistsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VinylsScreenAlbumsNetworkFailTest {

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
                albumsRepository = FakeFailNetworkAlbumsRepository()
            )
            artistsViewModel = ArtistsViewModel(
                artistsRepository = FakeFailNetworkArtistRepository()
            )
            VinylsApp(
                navController = navController,
                albumsViewModel = albumsViewModel,
                artistsViewModel = artistsViewModel
            )
        }
    }

    @Test
    fun albumsScreen_onNetworkFail_showError() {
        navigateToAlbumScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Albums.name)
        when (albumsViewModel.albumsUiState) {
            is AlbumsUiState.Success -> {

            }

            AlbumsUiState.Loading -> {
                composeTestRule.onNodeWithText("Cargando").assertExists()
            }

            AlbumsUiState.Error -> {
                composeTestRule.onNodeWithText("Ha ocurrido un error...").assertExists()
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