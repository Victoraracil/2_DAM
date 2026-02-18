package edu.practico.seriesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.practico.seriesapp.data.Repository
import edu.practico.seriesapp.data.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import edu.practico.seriesapp.data.model.Show
import edu.practico.seriesapp.data.remote.RemoteDataSource

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Error(val message: String) : DetailUiState()
    data class Success(val show: Show, val characters: List<Character>) : DetailUiState()
}


class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    private val remoteDatasource: RemoteDataSource

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        remoteDatasource = RemoteDataSource()
        repository = Repository(remoteDatasource)
    }

    fun getShowDetailById(id: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val show = repository.getShowDetailById(id)
                val characters = show.characters ?: emptyList()
                _uiState.value = DetailUiState.Success(
                    show = show,
                    characters = characters
                )
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}