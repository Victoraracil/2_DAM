package edu.victoraracil.apirestwords.ui

import edu.victoraracil.apirestwords.data.model.Word

data class UiState(
    val isLoading: Boolean = false,
    val selectedTab: Int = 0, // 0 = Todas, 1 = Favoritas
    val sortAscending: Boolean = true,
    val dialogWord: Word? = null,
    val errorMessage: String? = null
)
