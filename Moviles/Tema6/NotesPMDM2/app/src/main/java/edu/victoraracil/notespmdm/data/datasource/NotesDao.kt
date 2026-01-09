package edu.victoraracil.notespmdm.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.victoraracil.notespmdm.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY title COLLATE NOCASE ASC")
    fun getNotesSortedByTitleAsc(): Flow<List<Note>>

    @Query("SELECT * FROM notes ORDER BY title COLLATE NOCASE DESC")
    fun getNotesSortedByTitleDesc(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE idNote = :id")
    suspend fun getNoteById(id: Long): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Delete
    suspend fun deleteNote(note: Note): Int
}