package edu.javiercarrasco.coffeeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.javiercarrasco.coffeeapp.ui.componentes.AppTopBar
import edu.javiercarrasco.coffeeapp.ui.componentes.CommentDialog
import edu.javiercarrasco.coffeeapp.ui.navigation.Screens

@Composable
fun DetailCoffeeScreen(
    coffeeId: Int?,
    navHostController: NavHostController,
    detailCoffeeViewModel: DetailCoffeeViewModel = viewModel()
) {
    val coffeeDetail by detailCoffeeViewModel.stateCoffeeDetail.collectAsStateWithLifecycle()
    val comments by detailCoffeeViewModel.stateComments.collectAsStateWithLifecycle()
    val coffeeUiState by detailCoffeeViewModel.coffeeUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        coffeeId?.let {
            detailCoffeeViewModel.getCoffeeDetail(it)
            detailCoffeeViewModel.getCommentsByCoffeeId(it)
        }
    }

    AppTopBar(
        navIcon = {
            IconButton(
                onClick = {
                    navHostController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back"
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                if (coffeeDetail == null)
                    Text(
                        text = "No hay detalles para este café.",
                        color = Color.Red
                    )
                else {
                    Text(
                        text = "Nombre: ${coffeeDetail!!.coffeeName!!}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        AnnotatedString.fromHtml(coffeeDetail!!.coffeeDesc!!),
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonColors(
                            containerColor = Color(0xFF6F4E37),
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.LightGray
                        ),
                        onClick = {
                            detailCoffeeViewModel.showDialogComment(true)
                        }
                    ) {
                        Text("Añadir comentario")
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)
                    )
                    LazyColumn() {
                        items(comments) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            ) {
                                Text(
                                    text = it.user!!,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = it.comment!!
                                )
                            }
                        }
                    }
                }

                if (coffeeUiState.dialogComment) {
                    CommentDialog(
                        onDismiss = { detailCoffeeViewModel.showDialogComment(false) },
                        onCommentSubmitted = { commentText ->
                            coffeeId?.let {
                                detailCoffeeViewModel.putComment(
                                    it,
                                    commentText
                                )
                            }
                            detailCoffeeViewModel.showDialogComment(false)
                        }
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = {
                    detailCoffeeViewModel.logout()
                    navHostController.navigate(Screens.LoginScreen.route) {
                        popUpTo(navHostController.graph.id) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
            }
        })
}
