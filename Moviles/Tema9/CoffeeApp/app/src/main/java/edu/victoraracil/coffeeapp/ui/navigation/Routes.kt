package edu.victoraracil.coffeeapp.ui.navigation

sealed class Routes(val route: String) {
    object Login : Routes("login")
    object List : Routes("list")
    object Detail : Routes("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
}
