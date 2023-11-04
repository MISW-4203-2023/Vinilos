package com.team3.vinilos

import com.team3.vinilos.fake.FakeDataSource
import com.team3.vinilos.fake.FakeNetworkAlbumsRepository
import com.team3.vinilos.rules.TestDispatcherRule
import com.team3.vinilos.ui.viewmodels.AlbumsUiState
import com.team3.vinilos.ui.viewmodels.AlbumsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class AlbumsViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun albumsViewModel_getAlbums_verifyAlbumsUiStateSuccess(){
        runTest{
            val albumsViewModel = AlbumsViewModel(
                albumsRepository = FakeNetworkAlbumsRepository()
            )
            Assert.assertEquals(
                AlbumsUiState.Success(FakeDataSource.listAlbum),
                albumsViewModel.albumsUiState
            )
        }
    }
}