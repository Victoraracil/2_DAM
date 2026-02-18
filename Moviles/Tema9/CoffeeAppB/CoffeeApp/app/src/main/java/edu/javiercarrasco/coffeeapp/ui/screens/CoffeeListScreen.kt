package edu.javiercarrasco.coffeeapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.javiercarrasco.coffeeapp.R
import edu.javiercarrasco.coffeeapp.ui.componentes.AppTopBar
import edu.javiercarrasco.coffeeapp.ui.componentes.CoffeeListItems
import edu.javiercarrasco.coffeeapp.ui.navigation.Screens

@Composable
fun CoffeeListScreen(
    navHostController: NavHostController,
    coffeeListViewModel: CoffeeListViewModel = viewModel()
) {
    val uiState by coffeeListViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val showQuiz by coffeeListViewModel.showQuizDialog.collectAsState()
    val quizCoffee by coffeeListViewModel.quizCoffee.collectAsState()
    val quizOptions by coffeeListViewModel.quizOptions.collectAsState()

    LaunchedEffect(Unit) {
        // Cargar la lista de cafés al iniciar la pantalla y asegurarse de que se actualice
        // cada vez que se entre en esta pantalla para reflejar cambios en los comentarios.
        coffeeListViewModel.getCoffees()
    }

    AppTopBar(
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!uiState.isLoading) {
                    if (uiState.coffeeList.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = uiState.errorMessage.toString(),
                            color = Color.Red
                        )
                        Button(
                            onClick = { coffeeListViewModel.getCoffees() }
                        ) { Text(text = stringResource(R.string.coffee_retry))
                        }
                    } else CoffeeListItems(
                        uiState.coffeeList,
                        {
                            navHostController.navigate(Screens.DetailCoffeeScreen.createRoute(it.id!!))
                        })

                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(text = stringResource(R.string.coffee_loading))

                }

            }
        },
        actions = {

            val quizEnabled by coffeeListViewModel.quizEnabled.collectAsState()

            if (quizEnabled) {
                IconButton(
                    onClick = { coffeeListViewModel.generateQuiz() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = stringResource(R.string.quiz_content_description)
                    )
                }
            }

            IconButton(
                onClick = {
                    coffeeListViewModel.logout()
                    navHostController.navigate(Screens.LoginScreen.route) {
                        popUpTo(navHostController.graph.id) { inclusive = true }
                        launchSingleTop = true
                    }
                }

            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Logout,
                    contentDescription = stringResource(R.string.logout_content_description)

                )
            }
        })

        if (showQuiz && quizCoffee != null) {

        AlertDialog(
            onDismissRequest = { coffeeListViewModel.closeQuiz() },
            title = {
                Text(text = quizCoffee!!.coffeeName ?: "")
            },
            text = {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(quizOptions) { option ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val correct = coffeeListViewModel.checkQuizAnswer(option)

                                    Toast.makeText(
                                        context,
                                        if (correct) "¡Correcto!"
                                        else "Incorrecto. Era ${quizCoffee!!.coffeeName}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = option,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
            ,
            confirmButton = {
                TextButton(
                    onClick = {
                        coffeeListViewModel.closeQuiz()
                    }
                ) {
                    Text(stringResource(R.string.quiz_cancel))
                }
            }
        )
    }
}
