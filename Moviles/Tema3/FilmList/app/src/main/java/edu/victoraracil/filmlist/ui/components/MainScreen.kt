package edu.victoraracil.filmlist.ui.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.victoraracil.filmlist.R
import edu.victoraracil.filmlist.data.model.Film
import edu.victoraracil.filmlist.data.repository.FilmRepository
import edu.victoraracil.filmlist.viewmodel.FilmViewModel
import edu.victoraracil.filmlist.viewmodel.FilmViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    vm: FilmViewModel = viewModel(
        factory = FilmViewModelFactory(
            repository = FilmRepository(LocalContext.current)
        )
    )
) {

    val context = LocalContext.current
    val films by vm.listOfFilms.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var lastDeletedFilm by remember { mutableStateOf<Film?>(null) }
    var lastDeletedPosition by remember { mutableStateOf(-1) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(context.getString(R.string.app_name)) })
    }, snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->

        if (films.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = context.getString(R.string.warning_no_films))
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                itemsIndexed(films) { index, film ->

                    FilmItem(film = film, onClick = {
                        Toast.makeText(
                            context, film.title, Toast.LENGTH_SHORT
                        ).show()
                    }, onLongClick = {
                        lastDeletedFilm = film
                        lastDeletedPosition = index

                        vm.deleteFilm(film.id)

                        coroutineScope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = context.getString(
                                    R.string.txt_film_deleted, film.title
                                ), actionLabel = context.getString(R.string.txt_undo)
                            )

                            if (result == SnackbarResult.ActionPerformed) {
                                lastDeletedFilm?.let {
                                    vm.addFilm(lastDeletedPosition, it)
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}

