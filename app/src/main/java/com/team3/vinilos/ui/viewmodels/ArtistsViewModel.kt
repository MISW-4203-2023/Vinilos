package com.team3.vinilos.ui.viewmodels

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
import com.team3.vinilos.data.ArtistsRepository
import com.team3.vinilos.data.models.Artist
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface ArtistsUiState {
    data class Success(val artists: List<Artist>) : ArtistsUiState
    object Error: ArtistsUiState
    object Loading : ArtistsUiState
}
class ArtistsViewModel(private val artistsRepository: ArtistsRepository): ViewModel() {

    var artistUiState: ArtistsUiState by mutableStateOf(ArtistsUiState.Loading)
        private set

    init{
        getArtists()
    }

    fun getArtists(){
        viewModelScope.launch {
            artistUiState = ArtistsUiState.Loading
            artistUiState = try {
                ArtistsUiState.Success(artistsRepository.getArtists())
            } catch (e: IOException){
                ArtistsUiState.Error
            } catch (e: HttpException){
                ArtistsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VinylsApplication)
                val artistsRepository = application.container.artistsRepository
                ArtistsViewModel(artistsRepository)
            }
        }
    }

}

