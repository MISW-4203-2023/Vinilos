package com.team3.vinilos.view.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.team3.vinilos.R
import com.team3.vinilos.viewModel.AlbumsViewModel
import com.team3.vinilos.viewModel.ArtistViewModel
import com.team3.vinilos.viewModel.ArtistsViewModel

enum class VinylsAppScreen(@StringRes val title: Int) {
    Start(title = R.string.start_title),
    Artists(title = R.string.artists_title),
    Albums(title = R.string.albums_title),
    Collectors(title = R.string.collectors_title)
}

@Composable
fun VinylsAppBar(
    title: Int?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    logout: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { title?.let { Text(stringResource(it)) } },
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
            if (title != VinylsAppScreen.Start.title) {
                IconButton(onClick = logout) {
                    Icon(
                        painterResource(id = R.drawable.logout_24),
                        contentDescription = stringResource(R.string.exit_app)
                    )
                }
            }

        }
    )
}

@Composable
fun VinylsNavBar(
    navigate: (id: String) -> Unit,
    activeRouteName: String
) {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.album_24),
                    contentDescription = stringResource(R.string.albums_title)
                )
            },
            label = { Text(stringResource(R.string.albums_title)) },
            selected = activeRouteName.startsWith(VinylsAppScreen.Albums.name),
            onClick = { navigate(VinylsAppScreen.Albums.name) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.piano_24),
                    contentDescription = stringResource(R.string.artists_title)
                )
            },
            label = { Text(stringResource(R.string.artists_title)) },
            selected = activeRouteName.startsWith(VinylsAppScreen.Artists.name),
            onClick = { navigate(VinylsAppScreen.Artists.name) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.headphones_24),
                    contentDescription = stringResource(R.string.artists_title)
                )
            },
            label = { Text(stringResource(R.string.collectors_title)) },
            selected = activeRouteName.startsWith(VinylsAppScreen.Collectors.name),
            onClick = { navigate(VinylsAppScreen.Collectors.name) }
        )
    }
}

@Composable
fun VinylsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    artistsViewModel: ArtistsViewModel = viewModel(factory = ArtistsViewModel.Factory),
    albumsViewModel: AlbumsViewModel = viewModel(factory = AlbumsViewModel.Factory),
    artistViewModel: ArtistViewModel = viewModel(factory = ArtistViewModel.Factory),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val activeRouteName = backStackEntry?.destination?.route ?: VinylsAppScreen.Start.name
    var screenTitle: Int? = null
    try {
        val selectedScreen = VinylsAppScreen.valueOf(activeRouteName)
        screenTitle = selectedScreen.title
    } catch (_: IllegalArgumentException) {
    }
    Scaffold(
        topBar = {
            VinylsAppBar(
                title = screenTitle,
                canNavigateBack = screenTitle == null,
                navigateUp = { navController.navigateUp() },
                logout = { navController.navigate(VinylsAppScreen.Start.name) }
            )
        },
        bottomBar = {
            if (activeRouteName != VinylsAppScreen.Start.name) {
                VinylsNavBar(navigate = {
                    navController.navigate(it)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }, activeRouteName)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = VinylsAppScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        )
        {
            composable(route = VinylsAppScreen.Artists.name) {
                ArtistsScreen(
                    artistsViewModel.artistUiState,
                    retryAction = artistsViewModel::getArtists,
                    goToDetail = { navController.navigate("${VinylsAppScreen.Artists.name}/$it") }
                )
            }
            composable(
                route = "${VinylsAppScreen.Artists.name}/{artistId}",
                arguments = listOf(navArgument("artistId") { type = NavType.LongType })
            ) {
                val artistId = it.arguments?.getLong("artistId")
                artistId?.let {
                    LaunchedEffect(artistId) {
                        artistViewModel.getArtist(it)
                    }
                    ArtistScreen(
                        artistViewModel.artistUiState,
                        retryAction = {
                            artistViewModel.getArtist(
                                id = it
                            )
                        }
                    )
                }

            }
            composable(route = VinylsAppScreen.Start.name) {
                StartScreen(
                    onCollectorEntry = { navController.navigate(VinylsAppScreen.Albums.name) },
                    onGuessEntry = { navController.navigate(VinylsAppScreen.Albums.name) },
                    modifier = modifier
                )
            }
            composable(route = VinylsAppScreen.Albums.name) {
                AlbumsScreen(
                    albumsViewModel.albumsUiState,
                    retryAction = albumsViewModel::getAlbums
                )
            }
            composable(route = VinylsAppScreen.Collectors.name) {
                CollectorsScreen()
            }
        }
    }
}

