package edu.victoraracil.pr_clase_06a.ui.routes

sealed class AppRoutes(val route: String) {
    object Main : AppRoutes("main")
    object AddCar : AppRoutes("add_car")
    object EditCar : AppRoutes("edit_car/{carId}") {
        fun createRoute(carId: Int) = "edit_car/$carId"
    }
}
