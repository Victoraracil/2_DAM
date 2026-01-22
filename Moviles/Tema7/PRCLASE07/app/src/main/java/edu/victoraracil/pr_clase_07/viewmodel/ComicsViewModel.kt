package edu.victoraracil.pr_clase_07.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.victoraracil.pr_clase_07.data.model.Comic
import edu.victoraracil.pr_clase_07.data.repository.EditorialRepository
import edu.victoraracil.pr_clase_07.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ComicsViewModel(
    private val repository: EditorialRepository = EditorialRepository()
) : ViewModel() {

    private val _comicsState = MutableStateFlow<Resource<List<Comic>>>(Resource.Loading)
    val comicsState: StateFlow<Resource<List<Comic>>> = _comicsState.asStateFlow()

    fun cargarComics(editorialId: Int) {
        viewModelScope.launch {
            repository.getComicsByEditorial(editorialId).collect { estado ->
                _comicsState.value = estado
            }
        }
    }
}
