package edu.victoraracil.notespmdm.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.victoraracil.notespmdm.viewmodel.NotesViewModel

@Composable
fun AppNavigation(
    viewModel: NotesViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.NOTES_LIST
    ) {

        composable(AppRoutes.NOTES_LIST) {
            NotesListScreen(
                viewModel = viewModel,
                onAddNote = {
                    navController.navigate(AppRoutes.NOTE_DETAIL)
                },
                onNoteClick = { noteId ->
                    navController.navigate("${AppRoutes.NOTE_DETAIL}/$noteId")
                }
            )
        }

        composable(
            route = "${AppRoutes.NOTE_DETAIL}/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->

            val noteId = backStackEntry
                .arguments
                ?.getLong("noteId") ?: -1L

            NoteDetailScreen(
                viewModel = viewModel,
                noteId = noteId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

