package edu.victoraracil.filmlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.victoraracil.filmlist.data.repository.FilmRepository
import edu.victoraracil.filmlist.ui.components.MainScreen
import edu.victoraracil.filmlist.ui.theme.FilmListTheme
import edu.victoraracil.filmlist.viewmodel.FilmViewModel
import edu.victoraracil.filmlist.viewmodel.FilmViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmListTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val context = LocalContext.current

    val viewModel: FilmViewModel = viewModel(
        factory = FilmViewModelFactory(
            repository = FilmRepository(context)
        )
    )

    MainScreen(vm = viewModel)
}
