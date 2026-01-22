package edu.victoraracil.pr_clase_07.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.victoraracil.pr_clase_07.data.local.EditorialFavoritaEntity
import edu.victoraracil.pr_clase_07.data.local.toEntity
import edu.victoraracil.pr_clase_07.data.model.Editorial
import edu.victoraracil.pr_clase_07.data.repository.EditorialRepository
import edu.victoraracil.pr_clase_07.data.repository.FavoritosRepository
import edu.victoraracil.pr_clase_07.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditorialesViewModel(
    private val editorialRepository: EditorialRepository,
    private val favoritosRepository: FavoritosRepository
) : ViewModel() {

    private val _editorialesState = MutableStateFlow<Resource<List<Editorial>>>(Resource.Loading)
    val editorialesState: StateFlow<Resource<List<Editorial>>> = _editorialesState.asStateFlow()

    private val _favoritas = MutableStateFlow<List<EditorialFavoritaEntity>>(emptyList())
    val favoritas: StateFlow<List<EditorialFavoritaEntity>> = _favoritas.asStateFlow()

    init {
        cargarEditoriales()
        escucharFavoritas()
    }

    fun cargarEditoriales() {
        viewModelScope.launch {
            editorialRepository.getEditoriales().collect { estado ->
                _editorialesState.value = estado
            }
        }
    }

    private fun escucharFavoritas() {
        viewModelScope.launch {
            favoritosRepository.getAllFavoritas().collect { lista ->
                _favoritas.value = lista
            }
        }
    }

    fun toggleFavorito(editorial: Editorial) {
        viewModelScope.launch {
            val esFav = favoritas.value.any { it.id == editorial.id }
            if (esFav) {
                favoritosRepository.eliminarPorId(editorial.id)
            } else {
                favoritosRepository.insertar(editorial.toEntity())
            }
        }
    }
}
