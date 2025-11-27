package edu.victoraracil.prclase04.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.victoraracil.prclase04.ui.components.AppScaffold
import edu.victoraracil.prclase04.ui.navigation.NavScreens

@Composable
fun SettingsScreen(navController: NavHostController) {
    AppScaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = "Pantalla de Configuración")
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Preferencias ficticias:")
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                //Liempiar pila al volver al main
                navController.navigate(NavScreens.NavMainScreen.route) {
                    popUpTo(NavScreens.NavMainScreen.route) {
                        inclusive = true
                    }
                }
            }) {
                Text("Ir a Inicio")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.navigate(NavScreens.NavAboutScreen.route) }) {
                Text("Volver a Acerca de")
            }
        }
    }
}