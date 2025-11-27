package edu.victoraracil.prclase04.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.victoraracil.prclase04.ui.components.AppScaffold
import edu.victoraracil.prclase04.ui.navigation.NavScreens

@Composable
fun AboutScreen(navController: NavHostController) {
    val context = LocalContext.current

    //Evitar volver al main con el boton de atras
    BackHandler {
        Toast.makeText(
            context,
            "No se puede volver a la pantalla de inicio desde Acerca de",
            Toast.LENGTH_SHORT
        ).show()
    }

    AppScaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = "Acerca de la aplicación")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Nombre y apellidos: Tu Nombre Apellido")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate(NavScreens.NavSettingsScreen.route) }) {
                Text("Ir a Configuración")
            }
        }
    }
}