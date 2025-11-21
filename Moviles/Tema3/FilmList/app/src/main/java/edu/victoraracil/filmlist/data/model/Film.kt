package edu.victoraracil.filmlist.data.model

data class Film(
    val id: Int,
    val title: String,
    val year: Int,
    val duration: Int,
    val genre: String,
    val director: String,
    val cover: String
)