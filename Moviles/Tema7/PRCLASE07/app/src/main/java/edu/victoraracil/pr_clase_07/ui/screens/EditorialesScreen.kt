package edu.victoraracil.pr_clase_07.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import edu.victoraracil.pr_clase_07.data.local.DatabaseProvider
import edu.victoraracil.pr_clase_07.data.model.Editorial
import edu.victoraracil.pr_clase_07.data.repository.EditorialRepository
import edu.victoraracil.pr_clase_07.data.repository.FavoritosRepository
import edu.victoraracil.pr_clase_07.ui.navigation.Routes
import edu.victoraracil.pr_clase_07.utils.Resource
import edu.victoraracil.pr_clase_07.viewmodel.EditorialesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorialesScreen(navController: NavController) {

    val context = LocalContext.current

    val db = remember { DatabaseProvider.getDatabase(context) }
    val favoritosRepo = remember { FavoritosRepository(db.editorialFavoritaDao()) }
    val editorialRepo = remember { EditorialRepository() }

    val viewModel = remember {
        EditorialesViewModel(editorialRepo, favoritosRepo)
    }

    val state by viewModel.editorialesState.collectAsState()
    val favoritas by viewModel.favoritas.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Editoriales") }) }
    ) { paddingValues ->

        when (state) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            is Resource.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) { Text((state as Resource.Error).message) }
            }

            is Resource.Success -> {
                val editoriales = (state as Resource.Success<List<Editorial>>).data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(editoriales) { editorial ->
                        val esFav = favoritas.any { it.id == editorial.id }

                        EditorialItem(
                            editorial = editorial,
                            esFavorita = esFav,
                            onClick = {
                                navController.navigate("${Routes.COMICS}/${editorial.id}")
                            },
                            onToggleFavorito = {
                                viewModel.toggleFavorito(editorial)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EditorialItem(
    editorial: Editorial,
    esFavorita: Boolean,
    onClick: () -> Unit,
    onToggleFavorito: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(
                    model = editorial.logo,
                    contentDescription = "Logo editorial",
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = editorial.editorial, style = MaterialTheme.typography.titleMedium)
                    Text(text = "ID: ${editorial.id}")
                }

                IconButton(onClick = { onToggleFavorito() }) {
                    Icon(
                        imageVector = if (esFavorita) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = editorial.url,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(editorial.url))
                    context.startActivity(intent)
                }
            )
        }
    }
}
