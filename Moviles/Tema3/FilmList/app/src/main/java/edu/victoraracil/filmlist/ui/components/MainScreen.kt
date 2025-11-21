package edu.victoraracil.filmlist.ui.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.victoraracil.filmlist.data.model.Film
import edu.victoraracil.filmlist.viewmodel.FilmViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(vm: FilmViewModel) {

    val films by vm.listOfFilms.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Film List") }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Box(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {

            if (films.isEmpty()) {
                Text(
                    text = stringResource(R.string.warning_no_films),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    itemsIndexed(films) { index, film ->
                        FilmItem(
                            film = film,
                            index = index,
                            vm = vm,
                            snackbarHostState = snackbarHostState,
                            scope = scope,
                            context = context
                        )
                    }
                }
            }

        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmItem(
    film: Film,
    index: Int,
    vm: FilmViewModel,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    context: android.content.Context
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable(onClick = {
                Toast.makeText(context, film.title, Toast.LENGTH_SHORT).show()
            }, onLongClick = {
                vm.deleteFilm(film.id)
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = stringResource(R.string.txt_film_deleted, film.title),
                        actionLabel = stringResource(R.string.txt_undo)
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        vm.addFilm(index, film)
                    }
                }
            }),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {

            Image(
                painter = rememberAsyncImagePainter(film.cover),
                contentDescription = film.title,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp)
            )

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = film.title, fontWeight = FontWeight.Bold)
                Text(text = "Director: ${film.director}")
                Text(text = "Year: ${film.year}")
                Text(text = stringResource(R.string.txt_duration, film.duration))
                Text(text = "Genre: ${film.genre}")
            }
        }
    }
}
