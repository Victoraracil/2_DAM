package edu.victoraracil.demo2.viewmodel

import androidx.lifecycle.ViewModel
import edu.victoraracil.demo2.model.Recipe
import edu.victoraracil.demo2.model.sampleRecipes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipeViewModel : ViewModel() {
    // Estado de la lista de recetas
    private val _recipes = MutableStateFlow<List<Recipe>>(sampleRecipes)
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    // Receta actualmente seleccionada (para la pantalla de detalle)
    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe.asStateFlow()

    // Estado del diálogo modal
    data class DialogState(
        val visible: Boolean = false, val title: String = "", val message: String = ""
    )

    private val _dialogState = MutableStateFlow(DialogState())
    val dialogState: StateFlow<DialogState> = _dialogState.asStateFlow()

    // Funciones del ViewModel
    fun selectRecipe(recipe: Recipe) {
        _selectedRecipe.value = recipe
    }

    fun addRecipe(name: String, description: String): Boolean {
        if (name.isBlank() || description.isBlank()) {
            return false
        }
        val newId = (_recipes.value.maxOfOrNull { it.id } ?: 0) + 1
        val newRecipe =
            Recipe(newId, name, description, "https://picsum.photos/seed/$newId/200/200")
        _recipes.value = _recipes.value + newRecipe
        return true
    }

    // Elimina la receta seleccionada actualmente
    fun deleteSelectedRecipe() {
        _selectedRecipe.value?.let { recipe ->
            deleteRecipe(recipe)
            _selectedRecipe.value = null
        }
    }

    // Elimina una receta concreta (para pulsación larga o menú contextual)
    fun deleteRecipe(recipe: Recipe) {
        _recipes.value = _recipes.value.filter { it.id != recipe.id }
    }

    // Control del diálogo modal
    fun showDialog(title: String, message: String) {
        _dialogState.value = DialogState(true, title, message)
    }

    fun dismissDialog() {
        _dialogState.value = _dialogState.value.copy(visible = false)
    }
}
