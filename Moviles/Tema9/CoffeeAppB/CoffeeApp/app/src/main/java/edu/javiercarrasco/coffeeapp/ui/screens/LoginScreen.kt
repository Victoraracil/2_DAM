package edu.javiercarrasco.coffeeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.javiercarrasco.coffeeapp.ui.componentes.AppTopBar
import edu.javiercarrasco.coffeeapp.ui.componentes.LoginView
import es.javiercarrasco.ejemplologin.data.model.LoginRequest
import es.javiercarrasco.ejemplologin.data.model.LoginState

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()

    AppTopBar(content = {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (loginState is LoginState.Idle || loginState is LoginState.Error) {
                if (loginState is LoginState.Error) {
                    val message = (loginState as LoginState.Error).message
                    Text(message, color = Color.Red)
                }
                LoginView(
                    onClick = { name, password ->
                        loginViewModel.login(
                            LoginRequest(
                                user = name,
                                password = password
                            )
                        )
                    }
                )
            } else if (loginState is LoginState.Loading) {
                // Aquí se podría mostrar un indicador de carga si lo deseas
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
                Text(text = "Cargando...")
            }
        }
    })
}