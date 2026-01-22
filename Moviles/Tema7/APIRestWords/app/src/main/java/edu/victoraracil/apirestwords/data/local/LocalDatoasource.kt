package edu.victoraracil.apirestwords.data.local

import edu.victoraracil.apirestwords.data.model.Word
import kotlinx.coroutines.flow.Flow

class LocalDatoasource (private val dao: WordsDAO) {

    fun getAllWords(): Flow<List<Word>> =
        dao.getAllWords()

    //Obtener palabras favoritas ordenadas alfabéticamente
    fun getFavoriteWordsOrdered(): Flow<List<Word>> =
        dao.getFavoriteWords()

    //Insertar palabra favorita
    //(solo se llama cuando favorite = true)
    suspend fun insertFavorite(word: Word) {
        dao.insertWord(word)
    }

    //Eliminar palabra de favoritos
    suspend fun deleteFavorite(word: String) {
        dao.deleteWord(word)
    }
}
