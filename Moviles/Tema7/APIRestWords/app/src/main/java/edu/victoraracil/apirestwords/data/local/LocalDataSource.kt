package edu.victoraracil.apirestwords.data.local

import edu.victoraracil.apirestwords.data.model.Word
import kotlinx.coroutines.flow.Flow

class LocalDatasource(private val wordsDAO: WordsDAO) {

    fun getFavoritesAsc(): Flow<List<Word>> = wordsDAO.getAllWordsFavAsc()

    fun getFavoritesDesc(): Flow<List<Word>> = wordsDAO.getAllWordsFavDesc()

    suspend fun getWordById(id: Int): Word? {
        return wordsDAO.getWordById(id)
    }

    suspend fun insertWord(word: Word) {
        wordsDAO.insertWord(word)
    }

    suspend fun deleteWord(word: Word) {
        wordsDAO.deleteWord(word)
    }
}