package edu.victoraracil.pr_clase_07.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.victoraracil.pr_clase_07.ui.screens.ComicsScreen
import edu.victoraracil.pr_clase_07.ui.screens.EditorialesScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.EDITORIALES
    ) {
        composable(Routes.EDITORIALES) {
            EditorialesScreen(navController)
        }

        composable(
            route = "${Routes.COMICS}/{editorialId}",
            arguments = listOf(navArgument("editorialId") { type = NavType.IntType })
        ) { backStackEntry ->
            val editorialId = backStackEntry.arguments?.getInt("editorialId") ?: 0
            ComicsScreen(
                navController = navController, editorialId = editorialId,
                editorialNombre = TODO(),
                viewModel = TODO()
            )
        }
    }
}

