package edu.practico.seriesapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.practico.seriesapp.data.model.Character
import edu.practico.seriesapp.viewmodel.CharacterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    navController: NavController,
    viewModel: CharacterViewModel = viewModel()
)
 {
    val characters: List<Character> by viewModel.characters.collectAsState()
    val loading by viewModel.loading.collectAsState() // Estado de carga.
    val error by viewModel.error.collectAsState()

    // Estado del pull-to-refresh.
    val refreshState = rememberPullToRefreshState()

     LaunchedEffect(Unit) {
         viewModel.getCharacters()
     }

     Scaffold(
        topBar = { TopAppBar({ Text("Series") }) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else if (error != null) {
                Text(text = "Error: $error", color = Color.Red, modifier = Modifier.padding(16.dp))
            } else {
                PullToRefreshBox(
                    isRefreshing = loading, // Usa el estado de carga del ViewModel.
                    state = refreshState, // Estado del pull-to-refresh.
                    modifier = Modifier.fillMaxSize(),
                    onRefresh = { viewModel.getCharacters() } // Acción al refrescar.
                ) {
                    // Contenido que se puede refrescar
                    LazyColumn {
                        items(characters) { character ->
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                Text("Nombre: ${character.name}", Modifier.padding(8.dp))
                                Text("ID: ${character.id}", modifier = Modifier.padding(8.dp))
                            }
                        }
                    }

                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { viewModel.getCharacters() }) {
                Text("Actualizar")
            }
        }
    }
}