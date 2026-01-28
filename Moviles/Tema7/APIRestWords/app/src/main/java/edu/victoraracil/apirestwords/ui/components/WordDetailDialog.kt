package edu.victoraracil.apirestwords.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import edu.victoraracil.apirestwords.data.model.Word

@Composable
fun WordDetailDialog(
    word: Word, onDismiss: () -> Unit
) {
    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = onDismiss) {
            Text("OK")
        }
    }, title = { Text(word.word) }, text = { Text(word.definition) })
}
