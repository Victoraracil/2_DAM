package edu.victoraracil.apirestwords.data

import edu.victoraracil.apirestwords.data.local.LocalDatasource
import edu.victoraracil.apirestwords.data.model.Word
import edu.victoraracil.apirestwords.data.remote.RemoteDatasource
import kotlinx.coroutines.flow.Flow

class Repository(
    private val remoteDatasource: RemoteDatasource, private val localDatasource: LocalDatasource
) {

    //Todas las palabras (API + marcar favoritas)
    suspend fun getAllWords(sortAscending: Boolean): List<Word>? {
        val response = remoteDatasource.getAllWords()

        if (!response.isSuccessful) return null

        val remoteWords = response.body() ?: return emptyList()

        val result = mutableListOf<Word>()

        for (word in remoteWords) {
            val fav = localDatasource.getWordById(word.idWord)
            result.add(
                word.copy(
                    favorite = fav != null
                )
            )
        }

        return if (sortAscending) {
            result.sortedBy { it.word.lowercase() }
        } else {
            result.sortedByDescending { it.word.lowercase() }
        }
    }

    //Favoritas (solo Room)
    fun getFavWords(sortAscending: Boolean): Flow<List<Word>> {
        return if (sortAscending) {
            localDatasource.getFavoritesAsc()
        } else {
            localDatasource.getFavoritesDesc()
        }
    }

    //Marcar / desmarcar favorita
    suspend fun updateFavWord(word: Word) {
        val fav = localDatasource.getWordById(word.idWord)

        if (fav == null) {
            localDatasource.insertWord(word)
        } else {
            localDatasource.deleteWord(word)
        }
    }
}
