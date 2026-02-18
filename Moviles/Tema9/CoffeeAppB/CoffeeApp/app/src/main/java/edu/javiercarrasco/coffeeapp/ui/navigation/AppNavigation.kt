package edu.javiercarrasco.coffeeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.javiercarrasco.coffeeapp.ui.screens.CoffeeListScreen
import edu.javiercarrasco.coffeeapp.ui.screens.DetailCoffeeScreen
import edu.javiercarrasco.coffeeapp.ui.screens.LoginScreen
import edu.javiercarrasco.coffeeapp.ui.screens.LoginViewModel
import es.javiercarrasco.ejemplologin.data.model.LoginState

@Composable
fun AppNavigation(loginViewModel: LoginViewModel = viewModel()) {
    val navigationController = rememberNavController()
    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                navigationController.navigate(Screens.CoffeeListScreen.route) {
                    popUpTo(Screens.LoginScreen.route) { inclusive = true }
                }
            }

            is LoginState.Idle, is LoginState.Error -> {
                if (loginState is LoginState.Error) {
                    val message = (loginState as LoginState.Error).message
                }
            }

            is LoginState.Loading -> {
                // No se realiza ninguna acción específica durante la carga.
            }
        }
    }

    NavHost(navigationController, startDestination = Screens.LoginScreen.route) {
        composable(Screens.LoginScreen.route) {
            LoginScreen()
        }
        composable(Screens.CoffeeListScreen.route) {
            CoffeeListScreen(navigationController)
        }
        composable(Screens.DetailCoffeeScreen.route) {
            val coffeeId = it.arguments?.getString("coffeeId")?.toIntOrNull()
            coffeeId?.let {
                DetailCoffeeScreen(coffeeId, navigationController)
            }
        }
    }
}