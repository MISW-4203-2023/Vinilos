package com.team3.vinilos.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.team3.vinilos.ui.screens.VinilsApp
import com.team3.vinilos.ui.screens.VinilsAppScreen
import org.junit.Rule
import org.junit.Before
import org.junit.Test

class VinilysScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            VinilsApp(navController = navController)
        }
    }

    @Test
    fun vinylNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(VinilsAppScreen.Start.name)
    }

    @Test
    fun vinylNavHost_clickButtonCollector_navigatesToAlbumsScreen() {
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        navController.assertCurrentRouteName(VinilsAppScreen.Albums.name)
    }

    @Test
    fun vinylNavHost_clickNavButtonCollector_navigatesToCollectorScreen() {
        navigateToCollectorScreen()
        navController.assertCurrentRouteName(VinilsAppScreen.Collectors.name)
    }

    @Test
    fun vinylNavHost_clickNavButtonArtists_navigatesToArtistScreen() {
        navigateToArtistScreen()
        navController.assertCurrentRouteName(VinilsAppScreen.Artists.name)
    }

    @Test
    fun vinylNavHost_clickNavButtonAlbums_navigatesToAlbumsScreen() {
        navigateToAlbumScreen()
        navController.assertCurrentRouteName(VinilsAppScreen.Albums.name)
    }

    @Test
    fun vinylNavHost_clickNavButtonExit_navigatesToStartScreen() {
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        composeTestRule.onNodeWithContentDescriptionId(com.team3.vinilos.R.string.exit_app)
            .performClick()
        navController.assertCurrentRouteName(VinilsAppScreen.Start.name)
    }

    @Test
    fun vinylNavHost_verifyExitButtonNotShownOnStartScreen() {
        composeTestRule.onNodeWithContentDescriptionId(com.team3.vinilos.R.string.exit_app)
            .assertDoesNotExist()
    }

    @Test
    fun vinylNavHost_verifyBackButtonNotShownOnStartScreen() {
        composeTestRule.onNodeWithContentDescriptionId(com.team3.vinilos.R.string.back_button)
            .assertDoesNotExist()
    }

    @Test
    fun vinylNavHost_verifyBackButtonNotShownOnCollectorScreen() {
        navigateToCollectorScreen()
        composeTestRule.onNodeWithContentDescriptionId(com.team3.vinilos.R.string.back_button)
            .assertDoesNotExist()
    }

    @Test
    fun vinylNavHost_verifyBackButtonNotShownOnArtistScreen() {
        navigateToArtistScreen()
        composeTestRule.onNodeWithContentDescriptionId(com.team3.vinilos.R.string.back_button)
            .assertDoesNotExist()
    }

    @Test
    fun vinylNavHost_verifyBackButtonNotShownOnAlbumScreen() {
        navigateToAlbumScreen()
        composeTestRule.onNodeWithContentDescriptionId(com.team3.vinilos.R.string.back_button)
            .assertDoesNotExist()
    }


    /* funciones de navegaciones.................... */
    private fun navigateToCollectorScreen(){
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
    }

    private fun navigateToArtistScreen(){
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.artists_title)
            .performClick()
    }

    private fun navigateToAlbumScreen(){
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.collectors_title)
            .performClick()
        composeTestRule.onNodeWithStringId(com.team3.vinilos.R.string.albums_title)
            .performClick()
    }
}