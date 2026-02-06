package edu.victoraracil.coffeeapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.victoraracil.coffeeapp.R
import edu.victoraracil.coffeeapp.domain.model.Coffee
import edu.victoraracil.coffeeapp.viewmodel.MainViewModel

@Composable
fun CoffeeListScreen(
    onCoffeeSelected: (Int) -> Unit,
    viewModel: MainViewModel = viewModel()
) {

    val coffeeList = viewModel.coffeeState.collectAsState().value
    val context = LocalContext.current

    // Cargamos cafés cuando entramos a esta pantalla
    LaunchedEffect(Unit) {
        viewModel.loadCoffees()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = { viewModel.logout() },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = context.getString(R.string.txt_logout))
        }

        Text(
            text = context.getString(R.string.txt_list_title),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when {

            // -------- ESTADO 1: CARGANDO --------
            coffeeList.isEmpty() -> {
                Column {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = context.getString(R.string.txt_list_loading))
                }
            }

            // -------- ESTADO 2: LISTA VACÍA --------
            coffeeList.isEmpty() -> {
                Text(
                    text = context.getString(R.string.txt_list_empty)
                )
            }

            // -------- ESTADO 3: LISTADO REAL --------
            else -> {
                LazyColumn {
                    items(coffeeList) { coffee ->
                        CoffeeRow(
                            coffee = coffee,
                            onClick = { onCoffeeSelected(coffee.id ?: 0) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CoffeeRow(
    coffee: Coffee,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Column {
            coffee.coffeeName?.let { Text(text = it) }
            Text(text = "Comentarios: ${coffee.comments}")
        }
    }
}
