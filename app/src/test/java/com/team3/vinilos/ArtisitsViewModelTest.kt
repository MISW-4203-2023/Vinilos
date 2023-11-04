package com.team3.vinilos

import com.team3.vinilos.fake.FakeDataSource
import com.team3.vinilos.fake.FakeNetworkArtistsRepository
import com.team3.vinilos.rules.TestDispatcherRule
import com.team3.vinilos.ui.viewmodels.ArtistsUiState
import com.team3.vinilos.ui.viewmodels.ArtistsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ArtisitsViewModelTest  {
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    @Test
    fun artistsViewModel_getArtists_verifyArtisitsUiStateSuccess() =
        runTest {
            val artisitsViewModel = ArtistsViewModel(
                artistsRepository = FakeNetworkArtistsRepository()
            )
            assertEquals(
                ArtistsUiState.Success(FakeDataSource.listArtist),
                artisitsViewModel.artistUiState
            )
        }
}