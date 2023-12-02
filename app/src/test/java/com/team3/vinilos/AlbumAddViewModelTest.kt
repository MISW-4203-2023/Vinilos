package com.team3.vinilos

import com.team3.vinilos.fake.FakeNetworkAlbumAddRepository
import com.team3.vinilos.model.models.CreateAlbum
import com.team3.vinilos.model.repository.AlbumAddRepository
import com.team3.vinilos.viewModel.AlbumAddViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class AlbumAddViewModelTest {

    private lateinit var albumAddRepository: AlbumAddRepository
    private lateinit var viewModel: AlbumAddViewModel

    @Before
    fun setup() {
        albumAddRepository = FakeNetworkAlbumAddRepository()
        viewModel = AlbumAddViewModel(albumAddRepository)
    }

    @Test
    fun `addAlbum with valid input should update UI state`() = runBlockingTest {
        viewModel.updateField(CreateAlbum("AlbumName"))

        viewModel.addAlbum {
            assertEquals(true, viewModel.uiState.value.hasSent)
            assertEquals(false, viewModel.uiState.value.loading)
            assertEquals(false, viewModel.uiState.value.error)
            assertEquals(null, viewModel.uiState.value.errorMessage)
        }
        assertEquals(false, viewModel.uiState.value.loading)
    }
}