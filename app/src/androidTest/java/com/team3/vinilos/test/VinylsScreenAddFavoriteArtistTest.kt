package com.team3.vinilos.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.team3.vinilos.R
import com.team3.vinilos.test.fake.FakeNetworkArtistRepository
import com.team3.vinilos.test.fake.FakeNetworkArtistsRepository
import com.team3.vinilos.view.screens.VinylsApp
import com.team3.vinilos.view.screens.VinylsAppScreen
import com.team3.vinilos.viewModel.ArtistViewModel
import com.team3.vinilos.viewModel.ArtistsUiState
import com.team3.vinilos.viewModel.ArtistsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VinylsScreenAddFavoriteArtistTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController
    private lateinit var artistViewModel: ArtistViewModel
    private lateinit var artistsViewModel: ArtistsViewModel

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }

            artistViewModel = ArtistViewModel(
                artistRepository = FakeNetworkArtistRepository()
            )

            artistsViewModel = ArtistsViewModel(
                artistsRepository = FakeNetworkArtistsRepository()
            )
            VinylsApp(
                navController = navController,
                artistsViewModel = artistsViewModel,
                artistViewModel = artistViewModel
            )
        }
    }

    @Test
    fun add_favorite_artist() {
        val textQuitar = composeTestRule.activity.getString(R.string.quitar)
        navigateToArtistScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Artists.name)
        when (artistsViewModel.artistUiState) {
            is ArtistsUiState.Success -> {
                val artists = (artistsViewModel.artistUiState as ArtistsUiState.Success).artists
                    val i = 1
                    val artistName = artists[i].name

                    composeTestRule.onNodeWithTag("artist_list")
                        .performScrollToIndex(i)

                    composeTestRule.onNodeWithTag("btn $artistName")
                        .performClick()
                    composeTestRule.onNodeWithTag("favorite").assertExists()


                    composeTestRule.onNodeWithTag("favorite")
                        .performClick()

                    composeTestRule.onNodeWithContentDescription(textQuitar)
                        .assertExists()

            }

            ArtistsUiState.Loading -> {

            }

            ArtistsUiState.Error -> {

            }
        }
    }

    @Test
    fun remove_favorite_artist() {
        val textAgregar = composeTestRule.activity.getString(R.string.agregar)
        navigateToArtistScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Artists.name)
        when (artistsViewModel.artistUiState) {
            is ArtistsUiState.Success -> {
                val artists = (artistsViewModel.artistUiState as ArtistsUiState.Success).artists
                val i = 2
                val artistName = artists[i].name

                composeTestRule.onNodeWithTag("btn $artistName")
                    .performClick()

                composeTestRule.onNodeWithTag("favorite").assertExists()

                composeTestRule.onNodeWithTag("favorite")
                        .performClick()

                composeTestRule.onNodeWithTag("favorite")
                        .performClick()

                composeTestRule.onNodeWithContentDescription(textAgregar)
                    .assertExists()

            }
            ArtistsUiState.Loading -> {

            }

            ArtistsUiState.Error -> {

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