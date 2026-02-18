package edu.victoraracil.coffeeapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.victoraracil.coffeeapp.R
import edu.victoraracil.coffeeapp.domain.model.Coffee
import edu.victoraracil.coffeeapp.viewmodel.MainViewModel

@Composable
fun CoffeeListScreen(
    onCoffeeSelected: (Int) -> Unit, viewModel: MainViewModel = viewModel()
) {

    val coffeeList = viewModel.coffeeState.collectAsState().value
    val context = LocalContext.current
    val randomCoffee by viewModel.randomCoffee.collectAsStateWithLifecycle()
    val randomOptions by viewModel.randomOptions.collectAsStateWithLifecycle()
    val showDialog by viewModel.showGameDialog.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadCoffees()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = { viewModel.logout() }, modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(text = context.getString(R.string.txt_logout))
        }

        Button(
            onClick = { viewModel.generateRandomGame() },
            enabled = coffeeList.size >= 3
        ) {
            Text("Juego Aleatorio")
        }



        Text(
            text = context.getString(R.string.txt_list_title),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when {


            coffeeList.isEmpty() -> {
                Column {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = context.getString(R.string.txt_list_loading))
                }
            }

            coffeeList.isEmpty() -> {
                Text(
                    text = context.getString(R.string.txt_list_empty)
                )
            }

            else -> {
                LazyColumn {
                    items(coffeeList) { coffee ->
                        CoffeeRow(
                            coffee = coffee, onClick = { onCoffeeSelected(coffee.id ?: 0) })
                    }
                }
            }
        }
    }


    if (showDialog && randomCoffee != null) {

        AlertDialog(
            onDismissRequest = { },
            containerColor = androidx.compose.ui.graphics.Color.White,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            title = {
                Text(
                    text = "¿Cuál es la descripción correcta de ${randomCoffee?.coffeeName}?",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    randomOptions.forEach { description ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val correct = viewModel.checkAnswer(description)
                                    Toast.makeText(
                                        context,
                                        if (correct) "¡Correcto!" else "Incorrecto",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = androidx.compose.ui.graphics.Color(0xFFF5F5F5)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = description,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }


}

@Composable
fun CoffeeRow(
    coffee: Coffee, onClick: () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .padding(8.dp)) {
        Column {
            coffee.coffeeName?.let { Text(text = it) }
            Text(text = "Comentarios: ${coffee.comments}")
        }
    }
}
