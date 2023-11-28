package com.team3.vinilos.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.team3.vinilos.VinylsApplication
import com.team3.vinilos.model.models.Album
import com.team3.vinilos.model.models.CreateAlbum
import com.team3.vinilos.model.repository.AlbumAddRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class AlbumAddUiState(
    val createAlbum: CreateAlbum = CreateAlbum(),
    val loading: Boolean = false,
    val error: Boolean = false,
    val errorMessage: String? = null,
    val hasSent: Boolean = false
)

class AlbumAddViewModel(
    private val albumAddRepository: AlbumAddRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(AlbumAddUiState())
    val uiState: StateFlow<AlbumAddUiState> = _uiState.asStateFlow()

    fun addAlbum(success: () -> Unit) {
        _uiState.value = _uiState.value.copy(hasSent = true)
        if (_uiState.value.createAlbum.isValid()) {

            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(loading = true)
                try {
                    albumAddRepository.addAlbum(_uiState.value.createAlbum)
                    success()
                } catch (e: IOException) {
                    _uiState.value = _uiState.value.copy(error = true, errorMessage = e.message)
                } catch (e: HttpException) {
                    _uiState.value = _uiState.value.copy(error = true, errorMessage = e.message)
                } catch (e: Exception) {
                    Log.e("Error", e.message.toString(), e)
                    _uiState.value = _uiState.value.copy(error = true, errorMessage = e.message)
                } finally {
                    _uiState.value = _uiState.value.copy(loading = false)
                }
            }
        }
    }

    fun updateField(createAlbum: CreateAlbum) {
        _uiState.value = _uiState.value.copy(createAlbum = createAlbum)
    }

    fun resetFields() {
        _uiState.value = _uiState.value.copy(createAlbum = CreateAlbum())
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
