package edu.victoraracil.notespmdm.data.repository

import edu.victoraracil.notespmdm.data.datasource.LocalDatasource
import edu.victoraracil.notespmdm.data.model.Note
import kotlinx.coroutines.flow.Flow

class Repository(
    private val localDatasource: LocalDatasource
) {

    fun getNotes(sortOrder: SortOrder): Flow<List<Note>> {
        return when (sortOrder) {
            SortOrder.A_Z -> localDatasource.getNotesAsc()
            SortOrder.Z_A -> localDatasource.getNotesDesc()
        }
    }

    suspend fun getNoteById(id: Long): Note? {
        return localDatasource.getNoteById(id)
    }

    suspend fun insertNote(note: Note): Long {
        return localDatasource.insertNote(note)
    }

    suspend fun deleteNote(note: Note): Int {
        return localDatasource.deleteNote(note)
    }
}
