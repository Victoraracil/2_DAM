package edu.javiercarrasco.coffeeapp.ui.componentes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import edu.javiercarrasco.coffeeapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navIcon: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { navIcon() },
                title = { Text(stringResource(R.string.app_name)) },
                actions = { actions() }
            )
        },
        content = content
    )
}