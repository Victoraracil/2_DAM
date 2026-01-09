package edu.victoraracil.notespmdm.ui.screens

sealed class Screens(val route: String) {

    object NotesListScreen : Screens("notes_list")

    object NoteDetailScreen : Screens("note_detail/{id}") {
        fun createRoute(id: Long): String {
            return "note_detail/$id"
        }
    }
}
