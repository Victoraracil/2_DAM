package edu.victoraracil.apirestwords.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.victoraracil.apirestwords.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDAO {

    @Query("SELECT * FROM words ORDER BY word ASC")
    fun getAllWordsFavAsc(): Flow<List<Word>>

    @Query("SELECT * FROM words ORDER BY word DESC")
    fun getAllWordsFavDesc(): Flow<List<Word>>

    @Query("SELECT * FROM words WHERE idWord = :id")
    suspend fun getWordById(id: Int): Word?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)
}

