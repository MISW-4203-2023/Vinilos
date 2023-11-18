package com.team3.vinilos.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.team3.vinilos.VinylsApplication
import com.team3.vinilos.model.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class AppViewModel(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {
    val uiState: StateFlow<AppUiState> =
        userPreferencesRepository.isDarkMode.map { isDarkMode ->
            AppUiState(isDarkMode)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppUiState()
        )

    fun changeDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveDarkModePreference(isDarkMode)
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VinylsApplication)
                AppViewModel(application.userPreferencesRepository)
            }
        }
    }

}