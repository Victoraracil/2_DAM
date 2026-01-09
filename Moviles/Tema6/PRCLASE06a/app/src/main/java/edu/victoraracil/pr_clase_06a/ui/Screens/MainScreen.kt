package edu.victoraracil.pr_clase_06a.ui.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.victoraracil.pr_clase_06a.ui.components.AddBrandDialog
import edu.victoraracil.pr_clase_06a.ui.components.CarCard
import edu.victoraracil.pr_clase_06a.ui.viewmodel.CarsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: CarsViewModel,
    onAddCarClick: () -> Unit,
    onEditCarClick: (Int) -> Unit
) {
    val cars by viewModel.cars.collectAsState()
    val brands by viewModel.brands.collectAsState()

    //Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //Menú y diálogos
    var menuExpanded by remember { mutableStateOf(false) }
    var showAddBrandDialog by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text("PR-CLASE-06a") },
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menú"
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Añadir marca") },
                            onClick = {
                                showAddBrandDialog = true
                                menuExpanded = false
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (brands.isEmpty()) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Debes crear una marca antes de añadir coches"
                            )
                        }
                    } else {
                        onAddCarClick()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir coche"
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            if (cars.isEmpty()) {
                Text(
                    text = "No hay coches disponibles",
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn {
                    items(cars) { item ->
                        CarCard(
                            item = item,
                            onClick = {
                                onEditCarClick(item.car.idCar)
                            },
                            onFavorite = {
                                viewModel.updateCar(
                                    item.car.copy(
                                        favorite = if (item.car.favorite == 1) 0 else 1
                                    )
                                )
                            },
                            onDelete = {
                                viewModel.deleteCar(item.car)
                            }
                        )
                    }
                }
            }
        }
    }


    if (showAddBrandDialog) {
        AddBrandDialog(
            onDismiss = { showAddBrandDialog = false },
            onConfirm = { brandName ->
                viewModel.addBrand(brandName)
                showAddBrandDialog = false
            }
        )
    }
}
