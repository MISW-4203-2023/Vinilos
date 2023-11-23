package com.team3.vinilos.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.team3.vinilos.VinylsApplication
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.model.repository.FavoritePreferencesRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoritePreferencesRepository: FavoritePreferencesRepository) :
    ViewModel() {

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