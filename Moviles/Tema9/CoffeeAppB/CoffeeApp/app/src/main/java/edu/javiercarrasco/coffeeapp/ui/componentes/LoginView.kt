package edu.javiercarrasco.coffeeapp.ui.componentes

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginView(
    onClick: (name: String, password: String) -> Unit = { _, _ -> }
) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val error = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = user,
        onValueChange = { user = it },
        singleLine = true,
        label = { Text("Usuario") },
        isError = error.value && user.isBlank(),
        supportingText = {
            if (error.value && user.isBlank()) {
                Text("El usuario no puede estar vacío")
            }
        }
    )
    OutlinedTextField(
        value = pass,
        onValueChange = { pass = it },
        singleLine = true,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        isError = error.value && pass.isBlank(),
        supportingText = {
            if (error.value && pass.isBlank()) {
                Text("La contraseña no puede estar vacía")
            }
        }
    )
    Spacer(
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Button(onClick = {
        if (user.isNotBlank() && pass.isNotBlank()) {
            onClick(user, pass)
        } else {
            error.value = true
        }
    }) {
        Text("Iniciar Sesión")
    }
}