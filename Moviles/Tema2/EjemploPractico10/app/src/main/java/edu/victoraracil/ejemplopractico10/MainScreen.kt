package edu.victoraracil.ejemplopractico10

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainScreen() {
    var isLoggedIn by remember { mutableStateOf(false) }
    val ctxt = LocalContext.current

    if (!isLoggedIn) {
        // Se muestra el diálogo de inicio de sesión
        LoginDialog(
            onLogin = { user, pass ->
                // Aquí se maneja la lógica de inicio de sesión
                // Por ejemplo, verificar las credenciales
                if (user == "admin" && pass == "1234") {
                    isLoggedIn = true // Simulación de inicio de sesión exitoso
                    println("Inicio de sesión correcto. Usuario: $user, Contraseña: $pass")
                } else {
                    // Se muestra un mensaje de error o manejar el fallo de inicio de sesión
                    Toast.makeText(
                        ctxt,
                        ctxt.getString(R.string.txt_login_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    } else {
        // Contenido principal de la aplicación
        Text(text = ctxt.getString(R.string.txt_login_ok))
    }
}
