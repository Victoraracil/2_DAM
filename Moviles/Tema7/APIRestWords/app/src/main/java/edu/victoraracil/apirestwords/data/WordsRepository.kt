package edu.victoraracil.apirestwords.data

import edu.victoraracil.apirestwords.data.local.WordsDAO
import edu.victoraracil.apirestwords.data.model.Word
import edu.victoraracil.apirestwords.data.remote.ApiService
import kotlinx.coroutines.flow.Flow

class WordRepository(
    private val apiService: ApiService,
    private val wordDao: WordsDAO
) {
    // Obtenemos los datos de Room como un flujo (Flow)
    val allWords: Flow<List<Word>> = wordDao.getAllWords()
    val favoriteWords: Flow<List<Word>> = wordDao.getFavoriteWords()

    // Función para refrescar los datos de la API y guardarlos en Room
    suspend fun refreshWords() {
        try {
            val response = apiService.getAllWords()
            if (response.isSuccessful) {
                response.body()?.let { words ->
                    wordDao.insertWord(words)
                }
            }
        } catch (e: Exception) {
            // Aquí podrías manejar el error de red
        }
    }

    // Función para cambiar el estado de favorito
    suspend fun toggleFavorite(word: Word) {
        val updatedWord = word.copy(favorite = !word.favorite)
        wordDao.insertWord(words)
    }
}