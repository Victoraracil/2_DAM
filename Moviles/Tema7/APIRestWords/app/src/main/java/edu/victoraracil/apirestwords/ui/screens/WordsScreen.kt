package edu.victoraracil.apirestwords.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.victoraracil.apirestwords.R
import edu.victoraracil.apirestwords.ui.components.EmptyFavoritesState
import edu.victoraracil.apirestwords.ui.components.ErrorState
import edu.victoraracil.apirestwords.ui.components.LoadingState
import edu.victoraracil.apirestwords.ui.components.WordDetailDialog
import edu.victoraracil.apirestwords.ui.components.WordList
import edu.victoraracil.apirestwords.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordsScreen(viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val words by viewModel.words.collectAsStateWithLifecycle()
    val favWords by viewModel.favWords.collectAsStateWithLifecycle()

    val currentList = if (uiState.selectedTab == 0) words else favWords

    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.app_name)) }, actions = {
            IconButton(
                onClick = { viewModel.toggleSort() }, enabled = currentList.isNotEmpty()
            ) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = null)//Sort
            }

            IconButton(
                onClick = { viewModel.showRandomWord() }, enabled = currentList.isNotEmpty()
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)//Casino
            }
        })
    }, bottomBar = {
        NavigationBar {
            NavigationBarItem(
                selected = uiState.selectedTab == 0,
                onClick = { viewModel.selectTab(0) },
                label = { Text(stringResource(R.string.txt_optBottom_all)) },
                icon = {})
            NavigationBarItem(
                selected = uiState.selectedTab == 1,
                onClick = { viewModel.selectTab(1) },
                label = { Text(stringResource(R.string.txt_favorites)) },
                icon = {})
        }
    }) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        )
        when {
            uiState.isLoading -> LoadingState()

            uiState.errorMessage != null && uiState.selectedTab == 0 -> ErrorState(onRetry = viewModel::fetchWords)

            currentList.isEmpty() && uiState.selectedTab == 1 -> EmptyFavoritesState()

            else -> WordList(
                words = currentList,
                onWordClick = viewModel::showWordDetail,
                onFavClick = viewModel::toggleFavWord
            )
        }

        uiState.dialogWord?.let {
            WordDetailDialog(word = it, onDismiss = viewModel::dismissDialog)
        }
    }
}
