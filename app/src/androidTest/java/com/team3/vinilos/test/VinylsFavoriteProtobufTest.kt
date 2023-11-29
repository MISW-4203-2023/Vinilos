package com.team3.vinilos.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.team3.proto.FavoritePreferences
import com.team3.vinilos.test.fake.FakeFavoritePreferencesDataStore
import com.team3.vinilos.test.fake.FakeFavoritePreferencesRepository
import com.team3.vinilos.test.fake.FakeNetworkArtistRepository
import com.team3.vinilos.test.fake.FakeNetworkArtistsRepository
import com.team3.vinilos.view.screens.VinylsApp
import com.team3.vinilos.view.screens.VinylsAppScreen
import com.team3.vinilos.viewModel.ArtistViewModel
import com.team3.vinilos.viewModel.ArtistsUiState
import com.team3.vinilos.viewModel.ArtistsViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VinylsFavoriteProtobufTest {
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
    fun add_favorite_artist_protobuf() {
        val favoritePreferencesBuilder = FavoritePreferences.newBuilder()

        val artist = FavoritePreferences.Artist.newBuilder().setId(1).build()
        favoritePreferencesBuilder.addArtists(artist)
        val favoritePreferences = favoritePreferencesBuilder.build()
        val artists = favoritePreferences.artistsList

        assertEquals(1, artists.size)
        assertEquals(1, artists[0].id)
    }

    @Test
    fun remove_favorite_artist_protobuf() {
        val favoritePreferencesBuilder = FavoritePreferences.newBuilder()

        val artist = FavoritePreferences.Artist.newBuilder().setId(1).build()
        favoritePreferencesBuilder.addArtists(artist)
        var favoritePreferences = favoritePreferencesBuilder.build()

        favoritePreferencesBuilder.clearArtists()
        favoritePreferences = favoritePreferencesBuilder.build()
        val artists = favoritePreferences.artistsList

        assertEquals(0, artists.size)
    }

    @Test
    fun add_favorite_artist_and_verify_datastore() {
        val initialData = FavoritePreferences.getDefaultInstance()
        val fakeDataStore = FakeFavoritePreferencesDataStore(initialData)
        val fakeRepository = FakeFavoritePreferencesRepository(fakeDataStore)

        navigateToArtistScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Artists.name)
        when (artistsViewModel.artistUiState) {
            is ArtistsUiState.Success -> {
                val artists = (artistsViewModel.artistUiState as ArtistsUiState.Success).artists
                val i = 1
                val artistName = artists[i].name
                val artistId = artists[i].id

                composeTestRule.onNodeWithTag("artist_list")
                    .performScrollToIndex(i)

                composeTestRule.onNodeWithTag("btn $artistName")
                    .performClick()
                composeTestRule.onNodeWithTag("favorite").assertExists()


                composeTestRule.onNodeWithTag("favorite")
                    .performClick()

                runBlocking {
                    fakeRepository.agregarArtistaFavorito(artistId.toInt())
                }

                val result = runBlocking {
                    fakeRepository.favoritePreferencesFlow.first()
                }
                val artista = result.artistsList
                val artistFound = artista.any { it.id == artistId.toInt() }

                assertTrue(artistFound)
            }

            ArtistsUiState.Loading -> {

            }

            ArtistsUiState.Error -> {

            }
        }
    }

    @Test
    fun remove_favorite_artist_and_verify_datastore() {
        val initialData = FavoritePreferences.getDefaultInstance()
        val fakeDataStore = FakeFavoritePreferencesDataStore(initialData)
        val fakeRepository = FakeFavoritePreferencesRepository(fakeDataStore)

        navigateToArtistScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Artists.name)
        when (artistsViewModel.artistUiState) {
            is ArtistsUiState.Success -> {
                val artists = (artistsViewModel.artistUiState as ArtistsUiState.Success).artists
                val i = 1
                val artistName = artists[i].name
                val artistId = artists[i].id

                composeTestRule.onNodeWithTag("artist_list")
                    .performScrollToIndex(i)

                composeTestRule.onNodeWithTag("btn $artistName")
                    .performClick()
                composeTestRule.onNodeWithTag("favorite").assertExists()


                composeTestRule.onNodeWithTag("favorite")
                    .performClick()

                runBlocking {
                    fakeRepository.agregarArtistaFavorito(artistId.toInt())
                }

                runBlocking {
                    fakeRepository.removerArtistaFavorito(artistId.toInt())
                }

                val resultAfterRemove = runBlocking {
                    fakeRepository.favoritePreferencesFlow.first()
                }
                val artistRemoved = resultAfterRemove.artistsList.none { it.id == artistId.toInt() }
                assertFalse(artistRemoved)

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