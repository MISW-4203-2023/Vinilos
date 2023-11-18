package com.team3.vinilos.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.team3.vinilos.test.fake.FakeNetworkAlbumRepository
import com.team3.vinilos.test.fake.FakeNetworkAlbumsRepository
import com.team3.vinilos.test.fake.FakeNetworkArtistRepository
import com.team3.vinilos.test.fake.FakeNetworkArtistsRepository
import com.team3.vinilos.view.screens.VinylsApp
import com.team3.vinilos.view.screens.VinylsAppScreen
import com.team3.vinilos.viewModel.AlbumViewModel
import com.team3.vinilos.viewModel.AlbumsViewModel
import com.team3.vinilos.viewModel.ArtistUiState
import com.team3.vinilos.viewModel.ArtistViewModel
import com.team3.vinilos.viewModel.ArtistsUiState
import com.team3.vinilos.viewModel.ArtistsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class VinylsScreenArtistDetailTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var artistsViewModel: ArtistsViewModel
    private lateinit var artistViewModel: ArtistViewModel

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
            artistViewModel = ArtistViewModel(
                artistRepository = FakeNetworkArtistRepository()
            )
            VinylsApp(
                navController = navController,
                albumsViewModel = albumsViewModel,
                artistsViewModel = artistsViewModel,
                artistViewModel = artistViewModel,
                albumViewModel = albumViewModel
            )
        }
    }

    @Test
    fun artistDetailScreen_scrollOverAllList_findAllArtists() {
        navigateToArtistScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Artists.name)
        when (artistsViewModel.artistUiState) {
            is ArtistsUiState.Success -> {
                val artists = (artistsViewModel.artistUiState as ArtistsUiState.Success).artists

                for (i in 0 until 30) {
                    val i = Random.nextInt(1, 90)
                    val artistName = artists[i].name

                    composeTestRule.onNodeWithTag("artist_list")
                        .performScrollToIndex(i)

                    composeTestRule.onNodeWithTag("btn $artistName")
                        .performClick()
                    navController.assertCurrentRouteName("${VinylsAppScreen.Artists.name}/{artistId}")

                    composeTestRule.onNodeWithTag("artist_name")
                        .assertTextEquals(artistName)
                    composeTestRule.onNodeWithTag("back_button")
                        .performClick()
                }
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
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.artists_title)
            .performClick()
    }
}