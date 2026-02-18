package edu.javiercarrasco.coffeeapp.ui.componentes

import android.R.string.cancel
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun CommentDialog(
    onDismiss: () -> Unit,
    onCommentSubmitted: (String) -> Unit
) {
    var texto by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { false },
        title = { Text("Añadir comentario") },
        text = {
            Column {
                OutlinedTextField(
                    label = { Text("Comentario") },
                    value = texto,
                    onValueChange = { texto = it },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (texto.isNotBlank()) {
                    onCommentSubmitted(texto)
                    texto = ""
                }
            }) {
                Text("Publicar")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
                texto = ""
            }) {
                Text(LocalContext.current.getString(cancel))
            }
        }
    )
}