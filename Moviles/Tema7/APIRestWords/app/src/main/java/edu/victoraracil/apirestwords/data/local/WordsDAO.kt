package edu.victoraracil.apirestwords.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.victoraracil.apirestwords.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDAO {

    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<Word>>

    // Insertar palabra favorita
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: List<Word>)

    // Eliminar palabra de favoritos
    @Query("DELETE FROM words WHERE word = :word")
    suspend fun deleteWord(word: String)

    // Obtener todas las palabras favoritas
    @Query("SELECT * FROM words ORDER BY word ASC")
    fun getFavoriteWords(): Flow<List<Word>>

}
