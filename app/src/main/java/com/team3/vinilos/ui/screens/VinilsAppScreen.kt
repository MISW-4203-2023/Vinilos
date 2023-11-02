package com.team3.vinilos.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.team3.vinilos.R

enum class VinilsAppScreen(@StringRes val title: Int) {
    Start(title = R.string.start_title),
    Artists(title = R.string.artists_title),
    Albums(title = R.string.albums_title)
}

@Composable
fun VinilsAppBar(
    currentScreen: VinilsAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {  Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun VinilsApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {
            VinilsAppBar(
                currentScreen = VinilsAppScreen.valueOf(
                    backStackEntry?.destination?.route ?: VinilsAppScreen.Start.name
                ),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = VinilsAppScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        )
        {
            composable(route = VinilsAppScreen.Start.name) {
                StartScreen()
            }
            composable(route = VinilsAppScreen.Artists.name) {
                ArtistsScreen()
            }
            composable(route = VinilsAppScreen.Albums.name) {
                AlbumsScreen()
            }
        }
    }
}

