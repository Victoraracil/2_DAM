package edu.victoraracil.apirestwords.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.victoraracil.apirestwords.data.Repository
import edu.victoraracil.apirestwords.data.local.LocalDatasource
import edu.victoraracil.apirestwords.data.local.WordsDatabase
import edu.victoraracil.apirestwords.data.model.Word
import edu.victoraracil.apirestwords.data.remote.RemoteDatasource
import edu.victoraracil.apirestwords.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Repository
    private val repository: Repository

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words.asStateFlow()
    private val _favWords = MutableStateFlow<List<Word>>(emptyList())
    val favWords: StateFlow<List<Word>> = _favWords.asStateFlow()

    init {
        val db = WordsDatabase.getInstance(application)
        repository = Repository(
            RemoteDatasource(), LocalDatasource(db.wordDao())
        )
        observeFavWords()
        fetchWords()
    }


    private fun observeFavWords() {
        viewModelScope.launch {
            repository.getFavWords(_uiState.value.sortAscending).collect { list ->
                    _favWords.value = list
                }
        }
    }

    //Cargar palabras
    fun fetchWords() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true, errorMessage = null
            )

            val result = repository.getAllWords(_uiState.value.sortAscending)

            if (result == null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false, errorMessage = "Error al cargar palabras"
                )
            } else {
                _words.value = result
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    //Cambiar pestaña
    fun selectTab(tab: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    //Ordenar
    fun toggleSort() {
        val newOrder = !_uiState.value.sortAscending
        _uiState.value = _uiState.value.copy(sortAscending = newOrder)
        observeFavWords()
        fetchWords()
    }

    //Favorita
    fun toggleFavWord(word: Word) {
        viewModelScope.launch {
            repository.updateFavWord(word)
            fetchWords()
        }
    }

    //Mostrar detalle
    fun showWordDetail(word: Word) {
        _uiState.value = _uiState.value.copy(dialogWord = word)
    }

    fun dismissDialog() {
        _uiState.value = _uiState.value.copy(dialogWord = null)
    }

    //Palabra aleatoria
    fun showRandomWord() {
        val list = if (_uiState.value.selectedTab == 0) {
            words.value
        } else {
            favWords.value
        }

        if (list.isNotEmpty()) {
            _uiState.value = _uiState.value.copy(
                dialogWord = list.random()
            )
        }
    }
}
