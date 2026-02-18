package edu.practico.seriesapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.practico.seriesapp.ui.screens.CharacterScreen
import edu.practico.seriesapp.viewmodel.CharacterViewModel


@Composable
fun AppNavigation(characterViewModel: CharacterViewModel = viewModel()) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.CharacterScreen.route
    ) {
        composable(Screens.CharacterScreen.route) {
            CharacterScreen(
                navController = navController,
                viewModel = characterViewModel
            )
        }
    }
}
