package edu.victoraracil.demo2.model

data class Recipe(val id: Int, val name: String, val description: String, val imageUrl: String)

// Datos de ejemplo iniciales
val sampleRecipes = List(5) { index ->
    val id = index + 1
    Recipe(
        id = id,
        name = "Receta $id",
        description = "Descripción de la receta $id",
        imageUrl = "https://picsum.photos/seed/$id/200/200"  // imagen aleatoria fija
    )
}
