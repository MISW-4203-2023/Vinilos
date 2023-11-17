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
import com.team3.vinilos.test.fake.FakeNetworkCollectorRepository
import com.team3.vinilos.view.screens.VinylsApp
import com.team3.vinilos.view.screens.VinylsAppScreen
import com.team3.vinilos.viewModel.AlbumsViewModel
import com.team3.vinilos.viewModel.CollectorsUiState
import com.team3.vinilos.viewModel.CollectorsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VinlysScreenCollectorsTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var collectorsViewModel: CollectorsViewModel

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            albumsViewModel = AlbumsViewModel(
                albumsRepository = FakeNetworkAlbumsRepository()
            )
            collectorsViewModel = CollectorsViewModel(
                collectorsRepository = FakeNetworkCollectorRepository()
            )
            VinylsApp(
                navController = navController,
                albumsViewModel = albumsViewModel,
                collectorsViewModel = collectorsViewModel
            )
        }
    }

    @Test
    fun collectorsScreen_scrollOverAllList_findAllCollectors() {
        navigateToCollectorScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Collectors.name)
        when (collectorsViewModel.collectorsUiState) {
            is CollectorsUiState.Success -> {
                val collectors = (collectorsViewModel.collectorsUiState as CollectorsUiState.Success).collectors

                for (i in 0 until collectors.count()) {
                    composeTestRule.onNodeWithTag("collector_list")
                        .performScrollToIndex(i)
                    composeTestRule.onNodeWithText(collectors[i].name).assertExists()
                }

            }

            CollectorsUiState.Loading -> {

            }

            CollectorsUiState.Error -> {

            }
        }
    }

    private fun navigateToCollectorScreen() {
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
    }
}