package edu.victoraracil.notespmdm.data.datasource

import edu.victoraracil.notespmdm.data.model.Note
import kotlinx.coroutines.flow.Flow

class LocalDatasource(
    private val dao: NotesDao
) {

    fun getNotesAsc(): Flow<List<Note>> = dao.getNotesSortedByTitleAsc()

    fun getNotesDesc(): Flow<List<Note>> = dao.getNotesSortedByTitleDesc()

    suspend fun getNoteById(id: Long): Note? = dao.getNoteById(id)

    suspend fun insertNote(note: Note): Long = dao.insertNote(note)

    suspend fun deleteNote(note: Note): Int = dao.deleteNote(note)
}
