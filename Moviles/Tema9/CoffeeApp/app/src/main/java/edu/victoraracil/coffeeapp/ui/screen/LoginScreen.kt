package edu.victoraracil.coffeeapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.victoraracil.coffeeapp.R
import edu.victoraracil.coffeeapp.domain.model.LoginRequest
import edu.victoraracil.coffeeapp.domain.model.LoginState
import edu.victoraracil.coffeeapp.viewmodel.MainViewModel

@Composable
fun LoginScreen(
    viewModel: MainViewModel = viewModel(), onLoginSuccess: () -> Unit
// Callback para navegar al listado
) {
    LaunchedEffect(Unit) {
        viewModel.checkSessionOnStart()
    }

    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = context.getString(R.string.txt_login_title))

        Spacer(modifier = Modifier.width(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(context.getString(R.string.txt_username)) })

        Spacer(modifier = Modifier.width(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(context.getString(R.string.txt_password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.width(16.dp))

        when (loginState) {

            is LoginState.Loading -> {
                CircularProgressIndicator()
            }

            is LoginState.Error -> {
                Text(
                    text = context.getString(
                        R.string.txt_login_error, (loginState as LoginState.Error).message
                    )
                )

                Button(
                    onClick = {
                        viewModel.login(
                            LoginRequest(username, password)
                        )
                    }) {
                    Text(text = context.getString(R.string.txt_login_retry))
                }
            }

            else -> {
                Button(
                    onClick = {
                        viewModel.login(
                            LoginRequest(username, password)
                        )
                    }) {
                    Text(text = context.getString(R.string.txt_login_button))
                }
            }
        }
    }
}
