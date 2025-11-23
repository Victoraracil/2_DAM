package edu.victoraracil.filmlist.viewmodel

import androidx.lifecycle.ViewModel
import edu.victoraracil.filmlist.data.model.Film
import edu.victoraracil.filmlist.data.repository.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FilmViewModel(private val repository: FilmRepository) : ViewModel() {
    //Estado de la lista de películas utilizando backing property.
    private val _listOfFilms = MutableStateFlow(mutableListOf<Film>())
    val listOfFilms: StateFlow<List<Film>> = _listOfFilms.asStateFlow()

    init {
        //Cargar lista
        _listOfFilms.value = repository.readRawFile()
    }

    fun deleteFilm(filmId: Int) {
        _listOfFilms.value = _listOfFilms.value.filter { it.id != filmId }.toMutableList()
    }

    fun addFilm(pos: Int, film: Film) {
        val list = _listOfFilms.value.toMutableList()
        list.add(pos, film)
        _listOfFilms.value = list
    }
}
