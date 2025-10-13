package edu.victoraracil.practica1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import edu.victoraracil.practica1.ui.theme.Practica1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practica1 {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Formulario()
                    }
                }
            }
        }
    }
}

@Composable
fun Formulario() {
    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }

    val nombreCompleto by derivedStateOf {
        if (nombre.isNotBlank() && apellido.isNotBlank()) {
            "$nombre $apellido"
        } else {
            ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(text = "Formulario", style = MaterialTheme.typography.headlineMedium)

        //Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        //Apellido
        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellidos") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        //Etiqueta si hay nombre y apellido
        if (nombreCompleto.isNotEmpty()) {
            Text(
                text = "Nombre completo: $nombreCompleto",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )

            //Boton saludo
            Button(onClick = {
                Toast.makeText(context, "¡Hola, $nombreCompleto!", Toast.LENGTH_SHORT).show()
            }) {
                Text("Enviar saludo")
            }
        }

        //Boton limpiar
        Button(
            onClick = {
                nombre = ""
                apellido = ""
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
        ) {
            Text("Limpiar", color = Color.White)
        }
    }
}

//
fun Modifier.tarjetaRedonda(): Modifier = this
    .padding(8.dp)
    .clip(RoundedCornerShape(16.dp))
    .background(Color.White)
    .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))

