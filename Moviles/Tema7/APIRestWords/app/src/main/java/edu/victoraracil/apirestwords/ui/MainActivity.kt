package edu.victoraracil.apirestwords.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.victoraracil.apirestwords.ui.screens.WordsScreen
import edu.victoraracil.apirestwords.ui.theme.APIRestWordsTheme
import edu.victoraracil.apirestwords.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            APIRestWordsTheme {
                val vm: MainViewModel = viewModel()
                WordsScreen(viewModel = vm)
            }
        }
    }
}
