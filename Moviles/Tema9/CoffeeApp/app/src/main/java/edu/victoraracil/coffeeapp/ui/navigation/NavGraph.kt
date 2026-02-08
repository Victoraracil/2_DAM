package edu.victoraracil.coffeeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.victoraracil.coffeeapp.ui.screen.CoffeeDetailScreen
import edu.victoraracil.coffeeapp.ui.screen.CoffeeListScreen
import edu.victoraracil.coffeeapp.ui.screen.LoginScreen

@Composable
fun CoffeeNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController, startDestination = Routes.Login.route
    ) {

        composable(Routes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.List.route) {

                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                })
        }

        composable(Routes.List.route) {
            CoffeeListScreen(
                onCoffeeSelected = { coffeeId ->
                    navController.navigate(
                        Routes.Detail.createRoute(coffeeId)
                    )
                })
        }

        composable(Routes.Detail.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")!!.toInt()
            CoffeeDetailScreen(
                coffeeId = id, onBack = { navController.popBackStack() })
        }
    }
}
