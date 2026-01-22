package edu.victoraracil.apirestwords.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.victoraracil.apirestwords.data.model.Word
import kotlinx.coroutines.launch

class WordsViewModel (private val repository: WordRepository) : ViewModel() {

    // Estado para controlar si estamos en la pestaña "Todas" o "Favoritas"
    var isFavoritesScreen by mutableStateOf(false)

    // Estados de la UI
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    // Flujos de datos desde Room
    val allWords = repository.allWords.asLiveData()
    val favoriteWords = repository.favoriteWords.asLiveData()

    // Variable para el orden (true = A-Z, false = Z-A)
    var isAscending by mutableStateOf(true)

    init {
        fetchWords()
    }

    fun fetchWords() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                repository.refreshWords()
            } catch (e: Exception) {
                errorMessage = "Error de conexión"
            } finally {
                isLoading = false
            }
        }
    }

    fun toggleFavorite(word: Word) {
        viewModelScope.launch {
            repository.toggleFavorite(word)
        }
    }
}