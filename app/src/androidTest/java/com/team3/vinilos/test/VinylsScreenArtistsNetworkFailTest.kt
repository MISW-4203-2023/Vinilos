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
import com.team3.vinilos.ui.screens.VinylsApp
import com.team3.vinilos.ui.screens.VinylsAppScreen
import com.team3.vinilos.ui.viewmodels.AlbumsViewModel
import com.team3.vinilos.ui.viewmodels.ArtistsUiState
import com.team3.vinilos.ui.viewmodels.ArtistsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VinylsScreenArtistsNetworkFailTest {

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
    fun artistsScreen_onNetworkFail_showError() {
        navigateToArtistScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Artists.name)
        when (artistsViewModel.artistUiState) {
            is ArtistsUiState.Success -> {

            }

            ArtistsUiState.Loading -> {
                composeTestRule.onNodeWithText("Cargando...").assertExists()
            }

            ArtistsUiState.Error -> {
                composeTestRule.onNodeWithText("Ha ocurrido un error...").assertExists()
            }
        }
    }

    private fun navigateToArtistScreen() {
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.artists_title)
            .performClick()
    }
}