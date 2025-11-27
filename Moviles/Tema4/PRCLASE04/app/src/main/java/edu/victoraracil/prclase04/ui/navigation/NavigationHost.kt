package edu.victoraracil.prclase04.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.victoraracil.prclase04.ui.screens.AboutScreen
import edu.victoraracil.prclase04.ui.screens.DetailScreen
import edu.victoraracil.prclase04.ui.screens.HomeScreen
import edu.victoraracil.prclase04.ui.screens.SettingsScreen
import edu.victoraracil.prclase04.viewmodel.SharedViewModel

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    sharedViewModel: SharedViewModel = viewModel()
) {
    NavHost(
        navController = navController, startDestination = NavScreens.NavMainScreen.route
    ) {
        composable(NavScreens.NavMainScreen.route) {
            HomeScreen(navController, sharedViewModel)
        }

        composable(NavScreens.NavDetailScreen.route) {
            DetailScreen(navController, sharedViewModel)
        }

        composable(NavScreens.NavAboutScreen.route) {
            AboutScreen(navController)
        }

        composable(NavScreens.NavSettingsScreen.route) {
            SettingsScreen(navController)
        }
    }
}