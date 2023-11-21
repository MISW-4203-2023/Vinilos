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
import com.team3.vinilos.model.models.Collector
import com.team3.vinilos.model.repository.CollectorRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CollectorUiState {
    data class Success(val collector: Collector) : CollectorUiState
    object Error : CollectorUiState
    object Loading : CollectorUiState
}

class CollectorViewModel(
    private val collectorRepository: CollectorRepository
) : ViewModel() {

    var collectorUiState: CollectorUiState by mutableStateOf(CollectorUiState.Loading)
        private set

    fun getCollector(id: Long) {
        viewModelScope.launch {
            collectorUiState = CollectorUiState.Loading
            collectorUiState = try {
                CollectorUiState.Success(collectorRepository.getCollector(id))
            } catch (e: IOException) {
                CollectorUiState.Error
            } catch (e: HttpException) {
                CollectorUiState.Error
            } catch (e: Exception) {
                Log.e("Error", e.message.toString(), e)
                CollectorUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as VinylsApplication)
                val collectorRepository = application.container.collectorRepository
                CollectorViewModel(collectorRepository)
            }
        }
    }

}
