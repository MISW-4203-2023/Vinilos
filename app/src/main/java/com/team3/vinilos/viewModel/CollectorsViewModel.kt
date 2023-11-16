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
import com.team3.vinilos.model.repository.CollectorsRepository
import com.team3.vinilos.model.models.Collector
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CollectorsUiState {
    data class Success(val collectors: List<Collector>) : CollectorsUiState
    object Error : CollectorsUiState
    object Loading : CollectorsUiState
}

class CollectorsViewModel(private val collectorsRepository: CollectorsRepository) : ViewModel() {
    var collectorsUiState: CollectorsUiState by mutableStateOf(CollectorsUiState.Loading)
        private set

    init {
        getCollectors()
    }

    fun getCollectors() {
        viewModelScope.launch {
            collectorsUiState = CollectorsUiState.Loading
            collectorsUiState = try {
                CollectorsUiState.Success(collectorsRepository.getCollectors())
            } catch (e: IOException) {
                CollectorsUiState.Error
            } catch (e: HttpException) {
                CollectorsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VinylsApplication)
                val collectorsRepository = application.container.collectorsRepository
                CollectorsViewModel(collectorsRepository)
            }
        }
    }
}