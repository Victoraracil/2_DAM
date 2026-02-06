package edu.victoraracil.coffeeapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.victoraracil.coffeeapp.R
import edu.victoraracil.coffeeapp.domain.model.LoginRequest
import edu.victoraracil.coffeeapp.domain.model.LoginState
import edu.victoraracil.coffeeapp.ui.components.LoginDialog
import edu.victoraracil.coffeeapp.viewmodel.MainViewModel

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {

    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val coffeeList by viewModel.coffeeState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.checkSessionOnStart()
    }

    when (loginState) {

        is LoginState.Loading -> {
            Column {
                CircularProgressIndicator()
                Text(text = context.getString(R.string.txt_login_loading))
            }
        }

        is LoginState.Error -> {
            val message = (loginState as LoginState.Error).message

            Toast.makeText(
                context,
                context.getString(R.string.txt_login_error, message),
                Toast.LENGTH_LONG
            ).show()

            // Volvemos a mostrar login si hay error
            LoginDialog(
                onLogin = { user, pass ->
                    viewModel.login(LoginRequest(user, pass))
                }
            )
        }

        is LoginState.Idle -> {
            // Pantalla de login
            LoginDialog(
                onLogin = { user, pass ->
                    viewModel.login(LoginRequest(user, pass))
                }
            )
        }

        is LoginState.Success -> {

            LaunchedEffect(Unit) {
                viewModel.loadCoffees()
            }

            Column(modifier = Modifier.fillMaxSize()) {

                Text(
                    text = "Cafés cargados: ${coffeeList.size}"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Button(onClick = { viewModel.logout() }) {
                    Text(text = context.getString(R.string.txt_logout))
                }
            }
        }
    }
}
