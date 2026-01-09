package edu.victoraracil.notespmdm.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import edu.victoraracil.notespmdm.viewmodel.NotesViewModel
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import edu.victoraracil.notespmdm.data.repository.SortOrder
import edu.victoraracil.notespmdm.ui.components.NotesListContent
import edu.victoraracil.notespmdm.ui.components.NotesTopAppBar

@Composable
fun NotesListScreen(
    viewModel: NotesViewModel,
    onAddNote: () -> Unit,
    onNoteClick: (Long) -> Unit
) {
    val notes by viewModel.currentNotes

    Scaffold(
        topBar = {
            NotesTopAppBar(
                onSortAsc = {
                    viewModel.loadNotes(SortOrder.A_Z)
                },
                onSortDesc = {
                    viewModel.loadNotes(SortOrder.Z_A)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNote) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir nota"
                )
            }
        }
    ) { paddingValues ->
        NotesListContent(
            notes = notes,
            onNoteClick = onNoteClick,
            onDeleteNote = { note ->
                viewModel.deleteNote(note)
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}
