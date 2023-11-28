package com.team3.vinilos.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.team3.vinilos.model.models.CollectorAlbums
import com.team3.vinilos.test.fake.FakeNetworkCollectorRepository
import com.team3.vinilos.test.fake.FakeNetworkCollectorsRepository
import com.team3.vinilos.view.screens.VinylsApp
import com.team3.vinilos.view.screens.VinylsAppScreen
import com.team3.vinilos.viewModel.CollectorViewModel
import com.team3.vinilos.viewModel.CollectorsUiState
import com.team3.vinilos.viewModel.CollectorsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.random.Random

class VinylsScreenCollectorDetailTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    private lateinit var collectorsViewModel: CollectorsViewModel
    private lateinit var collectorViewModel: CollectorViewModel

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            collectorsViewModel = CollectorsViewModel(
                collectorsRepository = FakeNetworkCollectorsRepository()
            )
            collectorViewModel = CollectorViewModel(
                collectorRepository = FakeNetworkCollectorRepository()
            )

            VinylsApp(
                navController = navController,
                collectorsViewModel = collectorsViewModel,
                collectorViewModel = collectorViewModel
            )
        }
    }

    @Test
    fun collectorDetailScreen_clickOnDetails_validateTitle() {
        navigateToCollectorScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Collectors.name)
        when (collectorsViewModel.collectorsUiState) {
            is CollectorsUiState.Success -> {
                val collectors = (collectorsViewModel.collectorsUiState as CollectorsUiState.Success).collectors

                for (j in 0 until 30) {
                    val i = Random.nextInt(1, 90)
                    val collectorName = collectors[i].name
                    composeTestRule.onNodeWithTag("collector_list")
                        .performScrollToIndex(i)
                    composeTestRule.onNodeWithText(collectors[i].name).assertExists()

                    composeTestRule.onNodeWithTag("btn $collectorName")
                        .performClick()
                    navController.assertCurrentRouteName("${VinylsAppScreen.Collectors.name}/{collectorId}")

                    composeTestRule.onNodeWithTag("collector_name")
                        .assertTextEquals(collectorName)

                    composeTestRule.onNodeWithTag("back_button")
                        .performClick()
                }
            }

            CollectorsUiState.Loading -> {

            }

            CollectorsUiState.Error -> {

            }
        }
    }

    @Test
    fun collectorDetailScreen_clickOnDetails_validateTelefono() {
        navigateToCollectorScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Collectors.name)
        when (collectorsViewModel.collectorsUiState) {
            is CollectorsUiState.Success -> {
                val collectors = (collectorsViewModel.collectorsUiState as CollectorsUiState.Success).collectors

                for (j in 0 until 30) {
                    val i = Random.nextInt(1, 90)
                    val collectorName = collectors[i].name
                    val collectorTelefono = collectors[i].telephone
                    composeTestRule.onNodeWithTag("collector_list")
                        .performScrollToIndex(i)
                    composeTestRule.onNodeWithTag("btn $collectorName")
                        .performClick()
                    navController.assertCurrentRouteName("${VinylsAppScreen.Collectors.name}/{collectorId}")
                    composeTestRule.onNodeWithTag("collector_telefono")
                            .assertTextEquals(collectorTelefono.orEmpty())

                    composeTestRule.onNodeWithTag("back_button")
                        .performClick()
                }
            }

            CollectorsUiState.Loading -> {

            }

            CollectorsUiState.Error -> {

            }
        }
    }

    @Test
    fun collectorDetailScreen_clickOnDetails_validateCorreo() {
        navigateToCollectorScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Collectors.name)
        when (collectorsViewModel.collectorsUiState) {
            is CollectorsUiState.Success -> {
                val collectors = (collectorsViewModel.collectorsUiState as CollectorsUiState.Success).collectors

                for (j in 0 until 30) {
                    val i = Random.nextInt(1, 90)
                    val collectorName = collectors[i].name
                    val collectorCorreo = collectors[i].email
                    composeTestRule.onNodeWithTag("collector_list")
                        .performScrollToIndex(i)
                    composeTestRule.onNodeWithTag("btn $collectorName")
                        .performClick()
                    navController.assertCurrentRouteName("${VinylsAppScreen.Collectors.name}/{collectorId}")
                    composeTestRule.onNodeWithTag("collector_correo")
                        .assertTextEquals(collectorCorreo.orEmpty())

                    composeTestRule.onNodeWithTag("back_button")
                        .performClick()
                }
            }

            CollectorsUiState.Loading -> {

            }

            CollectorsUiState.Error -> {

            }
        }
    }

    @Test
    fun collectorDetailScreen_clickOnDetails_validateNTabs() {
        navigateToCollectorScreen()
        navController.assertCurrentRouteName(VinylsAppScreen.Collectors.name)
        when (collectorsViewModel.collectorsUiState) {
            is CollectorsUiState.Success -> {
                val collectors = (collectorsViewModel.collectorsUiState as CollectorsUiState.Success).collectors

                for (j in 0 until 30) {
                    val i = Random.nextInt(1, 90)
                    val collectorName = collectors[i].name
                    val collectorAlbums: List<CollectorAlbums>? = collectors[i].collectorAlbums

                    val collectorAlbumName = collectorAlbums?.get(0)?.album?.name

                    composeTestRule.onNodeWithTag("collector_list")
                        .performScrollToIndex(i)
                    composeTestRule.onNodeWithTag("btn $collectorName")
                        .performClick()
                    navController.assertCurrentRouteName("${VinylsAppScreen.Collectors.name}/{collectorId}")

                    composeTestRule.onNodeWithTag("Álbums", useUnmergedTree = true).performClick()
                    composeTestRule.onNodeWithTag("Álbums", useUnmergedTree = true).assertExists()
                    Thread.sleep(1000)

                    composeTestRule.onNodeWithTag("Artistas Favoritos", useUnmergedTree = true).performClick()
                    composeTestRule.onNodeWithTag("Artistas Favoritos", useUnmergedTree = true).assertExists()
                    Thread.sleep(1000)

                    composeTestRule.onNodeWithTag("Comentarios", useUnmergedTree = true).performClick()
                    composeTestRule.onNodeWithTag("Comentarios", useUnmergedTree = true).assertExists()

                    composeTestRule.onNodeWithTag("back_button")
                        .performClick()
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
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.artists_title)
            .performClick()
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
    }
}