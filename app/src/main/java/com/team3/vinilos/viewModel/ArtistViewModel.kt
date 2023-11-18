package com.team3.vinilos.viewModel

import android.util.Log
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
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.model.repository.ArtistRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface ArtistUiState {
    data class Success(val artist: Artist) : ArtistUiState
    object Error: ArtistUiState
    object Loading : ArtistUiState
}
class ArtistViewModel(private val artistRepository: ArtistRepository): ViewModel() {

    var artistUiState: ArtistUiState by mutableStateOf(ArtistUiState.Loading)
        private set



    fun getArtist(id: Long){
        viewModelScope.launch {
            artistUiState = ArtistUiState.Loading
            artistUiState = try {
                ArtistUiState.Success(artistRepository.getArtist(id))
            } catch (e: IOException){
                ArtistUiState.Error
            } catch (e: HttpException){
                ArtistUiState.Error
            } catch(e: Exception) {
                Log.e("Error", e.message.toString(),e)
                ArtistUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VinylsApplication)
                val artistRepository = application.container.artistRepository
                ArtistViewModel(artistRepository)
            }
        }
    }

}

