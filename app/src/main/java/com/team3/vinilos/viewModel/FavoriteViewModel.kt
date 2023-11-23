package com.team3.vinilos.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.team3.vinilos.VinylsApplication
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.model.repository.FavoritePreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoritePreferencesRepository: FavoritePreferencesRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteArtistUiState())
    val favoriteUiState: StateFlow<FavoriteArtistUiState> = _uiState.asStateFlow()

    fun isFavoriteArtist(id: Long){
        viewModelScope.launch {
            favoritePreferencesRepository.favoritePreferencesFlow.collect{preferences ->
                _uiState.update { currentState ->
                    currentState.copy(isFavorite = preferences.artistsList.filter { it.id == id.toInt() }.isNotEmpty() )
                }
            }
        }
    }

    fun agregarArtistaFavorito(artist: Artist) {
        viewModelScope.launch {
            favoritePreferencesRepository.agregarArtistaFavorito(artist)
        }
    }



    fun agregarFavorito(like: Boolean){
        viewModelScope.launch {
            favoritePreferencesRepository.updateFavorite(like)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VinylsApplication)
                FavoriteViewModel(application.favoritePreferencesRepository)
            }
        }
    }
}