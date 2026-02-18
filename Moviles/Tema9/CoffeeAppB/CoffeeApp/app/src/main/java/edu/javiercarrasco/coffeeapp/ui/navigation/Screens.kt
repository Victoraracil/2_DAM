package edu.javiercarrasco.coffeeapp.ui.navigation

// Definición de las pantallas de la aplicación.
sealed class Screens(val route: String) {
    object LoginScreen : Screens("login_screen")
    object CoffeeListScreen : Screens("home_screen")
    object DetailCoffeeScreen : Screens("detail_coffee_screen/{coffeeId}") {
        fun createRoute(coffeeId: Int) = "detail_coffee_screen/$coffeeId"
    }
}