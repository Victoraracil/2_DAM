package edu.victoraracil.filmlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.victoraracil.filmlist.data.repository.FilmRepository

class FilmViewModelFactory(private val repository: FilmRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") return FilmViewModel(repository) as T
    }
}