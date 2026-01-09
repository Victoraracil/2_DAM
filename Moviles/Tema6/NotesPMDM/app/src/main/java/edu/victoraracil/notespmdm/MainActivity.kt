package edu.victoraracil.notespmdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.victoraracil.notespmdm.ui.screens.AppNavigation
import edu.victoraracil.notespmdm.ui.theme.NotesPMDMTheme
import edu.victoraracil.notespmdm.viewmodel.NotesViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            NotesPMDMTheme {

                val notesViewModel: NotesViewModel = viewModel()

                AppNavigation(
                    viewModel = notesViewModel
                )
            }
        }
    }
}
