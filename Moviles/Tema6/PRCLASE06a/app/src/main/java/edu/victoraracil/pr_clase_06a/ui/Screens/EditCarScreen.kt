package edu.victoraracil.pr_clase_06a.ui.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import edu.victoraracil.pr_clase_06a.ui.components.CarFormScreen
import edu.victoraracil.pr_clase_06a.ui.viewmodel.CarsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCarScreen(
    carId: Int, carsViewModel: CarsViewModel, navController: NavController

) {
    val brands by carsViewModel.brands.collectAsState()
    val cars by carsViewModel.cars.collectAsState()

    val carWithBrand = cars.firstOrNull { it.car.idCar == carId }

    if (carWithBrand == null) {
        //Coche no encontrado
        navController.popBackStack()
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar coche") }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Volver"
                    )
                }
            })
        }) {}

    CarFormScreen(brands = brands, carWithBrand = carWithBrand, onSave = { car ->
        carsViewModel.updateCar(car)
        navController.popBackStack()
    }, onCancel = {
        navController.popBackStack()
    })

}
