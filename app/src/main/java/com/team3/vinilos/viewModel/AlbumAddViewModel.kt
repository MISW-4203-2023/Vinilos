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
import com.team3.vinilos.model.models.CreateAlbum
import com.team3.vinilos.model.repository.AlbumAddRepository
import com.team3.vinilos.model.repository.AlbumRepository
import com.team3.vinilos.model.repository.UserPreferencesRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AlbumAddUiState {
    data class Success(val album: CreateAlbum) : AlbumAddUiState
    object Error : AlbumAddUiState
    object Loading : AlbumAddUiState
}

class AlbumAddViewModel(
    private val albumAddRepository: AlbumAddRepository
) : ViewModel() {

    var albumAddUiState: AlbumAddUiState by mutableStateOf(AlbumAddUiState.Loading)
        private set

    fun addAlbum(album: CreateAlbum) {
        viewModelScope.launch {
            albumAddUiState = AlbumAddUiState.Loading
            albumAddUiState = try {
                Log.i("album a crear: ",album.toString())
                AlbumAddUiState.Success(albumAddRepository.addAlbum(album))
            } catch (e: IOException) {
                AlbumAddUiState.Error
            } catch (e: HttpException) {
                AlbumAddUiState.Error
            } catch (e: Exception) {
                Log.e("Error", e.message.toString(), e)
                AlbumAddUiState.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VinylsApplication)
                val albumAddRepository = application.container.albumAddRepository
                AlbumAddViewModel(albumAddRepository)
            }
        }
    }

}
