package edu.victoraracil.pr_clase_06a.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.victoraracil.pr_clase_06a.data.model.Brand
import edu.victoraracil.pr_clase_06a.data.model.Car
import edu.victoraracil.pr_clase_06a.data.model.CarWithBrand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarFormScreen(
    brands: List<Brand>,
    carWithBrand: CarWithBrand? = null,
    onSave: (Car) -> Unit,
    onCancel: () -> Unit
) {
    var model by remember { mutableStateOf(carWithBrand?.car?.model ?: "") }
    var motor by remember { mutableStateOf(carWithBrand?.car?.motor ?: "") }
    var year by remember { mutableStateOf(carWithBrand?.car?.year?.toString() ?: "") }
    var selectedBrandId by remember { mutableStateOf(carWithBrand?.brand?.idBrand) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Modelo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = motor,
            onValueChange = { motor = it },
            label = { Text("Motor") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Año") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = brands.firstOrNull { it.idBrand == selectedBrandId }?.name ?: "",
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
                        selectedBrandId = brand.idBrand
                        expanded = false
                    })
                }
            }
        }

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onCancel) {
                Text("Cancelar")
            }

            Button(onClick = {
                if (model.isBlank() || motor.isBlank() || year.isBlank() || selectedBrandId == null) {
                    error = "Todos los campos son obligatorios"
                    return@Button
                }

                val yearInt = year.toIntOrNull()
                if (yearInt == null || yearInt <= 0) {
                    error = "El año no es válido"
                    return@Button
                }

                onSave(
                    Car(
                        idCar = carWithBrand?.car?.idCar ?: 0,
                        model = model,
                        motor = motor,
                        year = yearInt,
                        idBrand = selectedBrandId!!
                    )
                )
            }) {
                Text(if (carWithBrand == null) "Añadir coche" else "Guardar cambios")
            }
        }
    }
}

