package edu.victoraracil.pr_clase_06a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.victoraracil.pr_clase_06a.ui.Screens.AddCarScreen
import edu.victoraracil.pr_clase_06a.ui.Screens.EditCarScreen
import edu.victoraracil.pr_clase_06a.ui.Screens.MainScreen
import edu.victoraracil.pr_clase_06a.ui.routes.AppRoutes
import edu.victoraracil.pr_clase_06a.ui.viewmodel.CarsViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            //ViewModel principal (datos persistentes)
            val carsViewModel: CarsViewModel = viewModel()

            NavHost(
                navController = navController, startDestination = AppRoutes.Main.route
            ) {

                composable(AppRoutes.Main.route) {
                    MainScreen(viewModel = carsViewModel, onAddCarClick = {
                        navController.navigate(AppRoutes.AddCar.route)
                    }, onEditCarClick = { carId ->
                        navController.navigate(
                            AppRoutes.EditCar.createRoute(carId)
                        )
                    })
                }

                composable(AppRoutes.AddCar.route) {
                    AddCarScreen(
                        navController = navController, carsViewModel = carsViewModel
                    )
                }

                composable(
                    route = AppRoutes.EditCar.route, arguments = listOf(
                        navArgument("carId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val carId = backStackEntry.arguments!!.getInt("carId")

                    EditCarScreen(
                        carId = carId, carsViewModel = carsViewModel, navController = navController
                    )
                }
            }
        }
    }
}

