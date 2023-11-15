package com.team3.vinilos.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.team3.vinilos.VinylsApplication
import com.team3.vinilos.model.repository.AlbumsRepository
import com.team3.vinilos.model.models.Album
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AlbumsUiState {
    data class Success(val albums: List<Album>) : AlbumsUiState
    object Error : AlbumsUiState
    object Loading : AlbumsUiState
}

class AlbumsViewModel(private val albumsRepository: AlbumsRepository) : ViewModel() {
    var albumsUiState: AlbumsUiState by mutableStateOf(AlbumsUiState.Loading)
        private set

    init {
        getAlbums()
    }

    fun getAlbums() {
        viewModelScope.launch {
            albumsUiState = AlbumsUiState.Loading
            albumsUiState = try {
                AlbumsUiState.Success(albumsRepository.getAlbums())
            } catch (e: IOException) {
                AlbumsUiState.Error
            } catch (e: HttpException) {
                AlbumsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VinylsApplication)
                val albumsRepository = application.container.albumsRepository
                AlbumsViewModel(albumsRepository)
            }
        }
    }
}