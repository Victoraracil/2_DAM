package edu.victoraracil.notespmdm.ui.screens


import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.victoraracil.notespmdm.R
import edu.victoraracil.notespmdm.data.model.Note
import edu.victoraracil.notespmdm.viewmodel.NotesViewModel
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    viewModel: NotesViewModel, noteId: Long, onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var noteDate by remember { mutableStateOf("") }

    var isEditing by remember { mutableStateOf(noteId == -1L) }
    var titleError by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        if (noteId != -1L) {
            viewModel.getNoteById(noteId) { note ->
                note?.let {
                    title = it.title
                    description = it.description
                    noteDate = it.date
                }
            }
        }
    }

    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return formatter.format(Date())
    }

    fun saveNote() {
        if (title.isBlank()) {
            titleError = true
            return
        }

        val note = Note(
            idNote = if (noteId == -1L) 0 else noteId,
            title = title,
            description = description,
            date = if (noteId == -1L) getCurrentDate() else noteDate
        )

        viewModel.saveNote(note)
        onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    when {
                        noteId == -1L -> stringResource(R.string.txt_onAddNote)

                        isEditing -> stringResource(R.string.txt_onEditNote)

                        else -> title
                    }
                )
            }, actions = {
                if (isEditing) {
                    IconButton(onClick = { saveNote() }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.txt_onSaveNote)
                        )
                    }
                } else {
                    IconButton(onClick = { isEditing = true }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.txt_onEditNote)
                        )
                    }
                }
            })
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    titleError = false
                },
                enabled = isEditing,
                isError = titleError,
                label = { Text(stringResource(R.string.txt_titleNote)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                enabled = isEditing,
                label = { Text(stringResource(R.string.txt_descriptionNote)) },
                modifier = Modifier.fillMaxWidth()
            )

            if (noteDate.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = noteDate, style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
