package edu.practico.seriesapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import edu.practico.seriesapp.Screens
import edu.practico.seriesapp.viewmodel.DetailUiState
import edu.practico.seriesapp.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    idShow: Int,
    viewModel: DetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(idShow) {
        viewModel.getShowDetailById(idShow)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Screen")}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (uiState) {

                is DetailUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is DetailUiState.Error -> {
                    Text((uiState as DetailUiState.Error).message)
                }

                is DetailUiState.Success -> {
                    val state = uiState as DetailUiState.Success

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        )
                        {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(state.show.coverImageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = state.show.title,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )

                            Text(
                                text = state.show.title,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(top = 12.dp)
                            )

                            Text(
                                text = state.show.synopsis ?: "Sin sinopsis",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }

                    Text(
                        text = "Characters",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    if (state.characters.isEmpty()) {
                        Text("Este show no tiene characters asociados")
                    } else {
                        LazyColumn {
                            items(state.characters) { ch -> //por cada uno
                                TextButton(
                                    onClick = {
                                        navController.navigate(
                                            Screens.CharacterScreen.createRoute(ch.id)
                                        )
                                    }
                                ) {
                                    Text(ch.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}