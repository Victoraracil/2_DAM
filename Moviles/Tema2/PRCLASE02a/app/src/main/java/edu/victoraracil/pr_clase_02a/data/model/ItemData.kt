package edu.victoraracil.pr_clase_02a.data.model

data class ItemData(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val isFavorite: Boolean = false
) {
    companion object {
        var itemIdCounter = 0
    }
}
