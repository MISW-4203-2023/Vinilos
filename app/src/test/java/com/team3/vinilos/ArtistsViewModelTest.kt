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

class ArtistsViewModelTest  {
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    @Test
    fun artistsViewModel_getArtists_verifyArtistsUiStateSuccess() =
        runTest {
            val artistsViewModel = ArtistsViewModel(
                artistsRepository = FakeNetworkArtistsRepository()
            )
            assertEquals(
                ArtistsUiState.Success(FakeDataSource.listArtist),
                artistsViewModel.artistUiState
            )
        }
}