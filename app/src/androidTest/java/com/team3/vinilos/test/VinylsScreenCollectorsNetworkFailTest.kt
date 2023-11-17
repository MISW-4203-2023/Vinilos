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
import com.team3.vinilos.test.fake.FakeFailNetworkCollectorRepository
import com.team3.vinilos.view.screens.VinylsApp
import com.team3.vinilos.view.screens.VinylsAppScreen
import com.team3.vinilos.viewModel.AlbumsViewModel
import com.team3.vinilos.viewModel.ArtistsViewModel
import com.team3.vinilos.viewModel.CollectorsUiState
import com.team3.vinilos.viewModel.CollectorsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VinylsScreenCollectorsNetworkFailTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var artistsViewModel: ArtistsViewModel
    private lateinit var collectorsViewModel: CollectorsViewModel

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
            collectorsViewModel = CollectorsViewModel(
                collectorsRepository = FakeFailNetworkCollectorRepository()
            )
            VinylsApp(
                navController = navController,
                albumsViewModel = albumsViewModel,
                artistsViewModel = artistsViewModel,
                collectorsViewModel = collectorsViewModel
            )
        }
    }

    @Test
    fun collectorsScreen_onNetworkFail_showError() {
        navigateToCollectorScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Collectors.name)
        when (collectorsViewModel.collectorsUiState) {
            is CollectorsUiState.Success -> {

            }

            CollectorsUiState.Loading -> {
                composeTestRule.onNodeWithText("Cargando...").assertExists()
            }

            CollectorsUiState.Error -> {
                composeTestRule.onNodeWithText("Ha ocurrido un error...").assertExists()
            }
        }
    }

    private fun navigateToCollectorScreen() {
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
    }
}