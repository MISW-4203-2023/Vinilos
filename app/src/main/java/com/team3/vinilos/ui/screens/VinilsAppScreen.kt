package com.team3.vinilos.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    Albums(title = R.string.albums_title),
    Collectors(title = R.string.collectors_title)
}

@Composable
fun VinilsAppBar(
    currentScreen: VinilsAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    logout: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
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
        },
        actions = {
            if (currentScreen.title != VinilsAppScreen.Start.title) {
                IconButton(onClick = logout) {
                    Icon(
                        painterResource(id = R.drawable.logout_24),
                        contentDescription = "Salir de la aplicación"
                    )
                }
            }
        }
    )
}

@Composable
fun VinilsNavBar(navController: NavHostController, activeRouteName: String) {
    NavigationBar() {
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.album_24),
                    contentDescription = "Album"
                )
            },
            label = { Text(stringResource(R.string.albums_title)) },
            selected = activeRouteName == VinilsAppScreen.Albums.name,
            onClick = { navController.navigate(VinilsAppScreen.Albums.name) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.piano_24),
                    contentDescription = "Album"
                )
            },
            label = { Text(stringResource(R.string.artists_title)) },
            selected = activeRouteName == VinilsAppScreen.Artists.name,
            onClick = { navController.navigate(VinilsAppScreen.Artists.name) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.headphones_24),
                    contentDescription = "Album"
                )
            },
            label = { Text(stringResource(R.string.collectors_title)) },
            selected = activeRouteName == VinilsAppScreen.Collectors.name,
            onClick = { navController.navigate(VinilsAppScreen.Collectors.name) }
        )
    }
}

@Composable
fun VinilsApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val activeRouteName = backStackEntry?.destination?.route ?: VinilsAppScreen.Start.name
    Scaffold(
        topBar = {
            VinilsAppBar(
                currentScreen = VinilsAppScreen.valueOf(activeRouteName),
                canNavigateBack = false,
                navigateUp = { navController.navigateUp() },
                logout = { navController.navigate(VinilsAppScreen.Start.name) }
            )
        },
        bottomBar = {
            if (activeRouteName != VinilsAppScreen.Start.name) {
                VinilsNavBar(navController, activeRouteName)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = VinilsAppScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        )
        {
            composable(route = VinilsAppScreen.Start.name) {
                StartScreen(
                    onCollectorEntry = { navController.navigate(VinilsAppScreen.Albums.name) },
                    onGuessEntry = { navController.navigate(VinilsAppScreen.Albums.name) },
                    modifier = modifier
                )
            }
            composable(route = VinilsAppScreen.Artists.name) {
                ArtistsScreen()
            }
            composable(route = VinilsAppScreen.Albums.name) {
                AlbumsScreen()
            }
            composable(route = VinilsAppScreen.Collectors.name) {
                CollectorsScreen()
            }
        }
    }
}

