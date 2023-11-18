package com.team3.vinilos.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.team3.vinilos.VinylsApplication
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.repository.AlbumRepository
import com.team3.vinilos.model.repository.UserPreferencesRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AlbumUiState {
    data class Success(val album: Album) : AlbumUiState
    object Error : AlbumUiState
    object Loading : AlbumUiState
}

class AlbumViewModel(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    var albumUiState: AlbumUiState by mutableStateOf(AlbumUiState.Loading)
        private set

    fun getAlbum(id: Long) {
        viewModelScope.launch {
            albumUiState = AlbumUiState.Loading
            albumUiState = try {
                AlbumUiState.Success(albumRepository.getAlbum(id))
            } catch (e: IOException) {
                AlbumUiState.Error
            } catch (e: HttpException) {
                AlbumUiState.Error
            } catch (e: Exception) {
                Log.e("Error", e.message.toString(), e)
                AlbumUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VinylsApplication)
                val albumRepository = application.container.albumRepository
                AlbumViewModel(albumRepository)
            }
        }
    }

}
