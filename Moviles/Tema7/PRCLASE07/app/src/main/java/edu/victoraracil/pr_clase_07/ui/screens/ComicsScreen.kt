package edu.victoraracil.pr_clase_07.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import edu.victoraracil.pr_clase_07.data.model.Comic
import edu.victoraracil.pr_clase_07.utils.Resource
import edu.victoraracil.pr_clase_07.viewmodel.ComicsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicsScreen(
    navController: NavController,
    editorialId: Int,
    viewModel: ComicsViewModel = ComicsViewModel()
) {
    val state by viewModel.comicsState.collectAsState()

    LaunchedEffect(editorialId) {
        viewModel.cargarComics(editorialId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cómics") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
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
                val comics = (state as Resource.Success<List<Comic>>).data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(comics) { comic ->
                        ComicItem(comic)
                    }
                }
            }
        }
    }
}

@Composable
private fun ComicItem(comic: Comic) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = comic.cover,
                contentDescription = "Portada",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = comic.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "Autor: ${comic.author}")

                Spacer(modifier = Modifier.height(8.dp))

                AsyncImage(
                    model = comic.editorial.logo,
                    contentDescription = "Logo editorial",
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}
