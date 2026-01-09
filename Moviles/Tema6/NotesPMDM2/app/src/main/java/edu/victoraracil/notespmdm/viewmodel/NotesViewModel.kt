package edu.victoraracil.notespmdm.viewmodel


import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.victoraracil.notespmdm.data.database.NotesDatabase
import edu.victoraracil.notespmdm.data.datasource.LocalDatasource
import edu.victoraracil.notespmdm.data.model.Note
import edu.victoraracil.notespmdm.data.repository.Repository
import edu.victoraracil.notespmdm.data.repository.SortOrder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = NotesDatabase.getDatabase(application).notesDao()

    private val localDatasource = LocalDatasource(dao)
    private val repository = Repository(localDatasource)

    private val _currentNotes = mutableStateOf<List<Note>>(emptyList())
    val currentNotes: State<List<Note>> = _currentNotes

    private var currentSortOrder by mutableStateOf(SortOrder.A_Z)

    init {
        loadNotes()
    }

    fun loadNotes(sortOrder: SortOrder = currentSortOrder) {
        currentSortOrder = sortOrder

        viewModelScope.launch {
            repository.getNotes(sortOrder).collectLatest { notes ->
                _currentNotes.value = notes
            }
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun getNoteById(id: Long, onResult: (Note?) -> Unit) {
        viewModelScope.launch {
            onResult(repository.getNoteById(id))
        }
    }
}