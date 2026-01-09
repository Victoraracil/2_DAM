package edu.victoraracil.notespmdm.ui.screens


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import edu.victoraracil.notespmdm.R
import edu.victoraracil.notespmdm.data.model.Note
import edu.victoraracil.notespmdm.data.repository.SortOrder
import edu.victoraracil.notespmdm.ui.components.NotesListContent
import edu.victoraracil.notespmdm.ui.components.NotesTopAppBar
import edu.victoraracil.notespmdm.viewmodel.NotesViewModel
import kotlinx.coroutines.launch


@Composable
fun NotesListScreen(
    viewModel: NotesViewModel, onAddNote: () -> Unit, onNoteClick: (Long) -> Unit
) {
    val notes by viewModel.currentNotes
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var lastDeletedNote by remember { mutableStateOf<Note?>(null) }
    val undoText = stringResource(R.string.txt_undo)
    val context = LocalContext.current
    var currentSortOrder by remember { mutableStateOf(SortOrder.A_Z) }



    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, topBar = {
        NotesTopAppBar(
            currentSortOrder = currentSortOrder, onSortChange = { newOrder ->
                currentSortOrder = newOrder
                viewModel.loadNotes(newOrder)
            })
    }, floatingActionButton = {
        FloatingActionButton(onClick = onAddNote) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.txt_onAddNote)
            )
        }
    }) { paddingValues ->
        NotesListContent(
            notes = notes, onNoteClick = onNoteClick, onDeleteNote = { note ->

                lastDeletedNote = note
                viewModel.deleteNote(note)

                val deletedMessage = context.getString(
                    R.string.txt_noteDeleted, note.title
                )

                coroutineScope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = deletedMessage, actionLabel = undoText
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        lastDeletedNote?.let {
                            viewModel.saveNote(it)
                        }
                    }

                    lastDeletedNote = null
                }
            }, modifier = Modifier.padding(paddingValues)
        )
    }
}