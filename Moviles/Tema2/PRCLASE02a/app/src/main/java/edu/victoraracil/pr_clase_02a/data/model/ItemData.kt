package edu.victoraracil.pr_clase_02a.data.model

data class Item(
    val id: Int,
    val title: String,
    val subtitle: String,
    val imagen: String,
    var isFavorite: Boolean = false
)