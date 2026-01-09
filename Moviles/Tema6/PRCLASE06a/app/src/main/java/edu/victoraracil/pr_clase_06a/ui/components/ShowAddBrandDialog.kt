package edu.victoraracil.pr_clase_06a.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AddBrandDialog(
    onDismiss: () -> Unit, onConfirm: (String) -> Unit
) {
    var brandName by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }


    AlertDialog(onDismissRequest = onDismiss, title = { Text("Nueva marca") }, text = {
        OutlinedTextField(
            value = brandName,
            onValueChange = {
                brandName = it
                isError = false
            },
            label = { Text("Nombre de la marca") },
            singleLine = true,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text("El nombre no puede estar vacío")
                }
            })
    }, confirmButton = {
        TextButton(
            enabled = !isSaving, onClick = {
                if (brandName.isBlank()) {
                    isError = true
                } else {
                    isSaving = true
                    onConfirm(brandName.trim())
                }
            }) {
            Text("Guardar")
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text("Cancelar")
        }
    })
}
