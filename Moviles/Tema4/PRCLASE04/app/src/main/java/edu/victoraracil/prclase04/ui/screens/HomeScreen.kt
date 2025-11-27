package edu.victoraracil.prclase04.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.victoraracil.prclase04.ui.components.AppScaffold
import edu.victoraracil.prclase04.ui.navigation.NavScreens
import edu.victoraracil.prclase04.viewmodel.SharedViewModel

@Composable
fun HomeScreen(navController: NavHostController, sharedViewModel: SharedViewModel) {
    AppScaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Bienvenido a PR-CLASE-04")
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = sharedViewModel.name,
                onValueChange = { sharedViewModel.updateName(it) },
                label = { Text("Introduce tu nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(NavScreens.NavDetailScreen.route) },
                enabled = sharedViewModel.name.isNotBlank()
            ) {
                Text("Ir a detalle")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate(NavScreens.NavAboutScreen.route) }) {
                Text("Acerca de")
            }
        }
    }
}