package edu.victoraracil.pr_clase_06a.ui.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.victoraracil.pr_clase_06a.data.model.Car
import edu.victoraracil.pr_clase_06a.ui.viewmodel.CarFormViewModel
import edu.victoraracil.pr_clase_06a.ui.viewmodel.CarsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(
    carsViewModel: CarsViewModel,
    navController: NavController,
    viewModel: CarFormViewModel = viewModel()
) {
    val brands by carsViewModel.brands.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Añadir coche") }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Volver"
                    )
                }
            })
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = viewModel.model,
                onValueChange = viewModel::onModelChange,
                label = { Text("Modelo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.motor,
                onValueChange = viewModel::onMotorChange,
                label = { Text("Motor") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.year,
                onValueChange = viewModel::onYearChange,
                label = { Text("Año") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = brands.firstOrNull { it.idBrand == viewModel.selectedBrandId }?.name
                    ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Marca") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded, onDismissRequest = { expanded = false }) {
                    brands.forEach { brand ->
                        DropdownMenuItem(text = { Text(brand.name) }, onClick = {
                            viewModel.onBrandSelected(brand.idBrand)
                            expanded = false
                        })
                    }
                }
            }

            viewModel.errorMessage?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(), onClick = {
                    if (viewModel.validate()) {
                        carsViewModel.addCar(
                            Car(
                                model = viewModel.model,
                                motor = viewModel.motor,
                                year = viewModel.year.toInt(),
                                idBrand = viewModel.selectedBrandId!!
                            )
                        )
                        navController.popBackStack()
                    }
                }) {
                Text("Guardar coche")
            }
        }
    }
}
