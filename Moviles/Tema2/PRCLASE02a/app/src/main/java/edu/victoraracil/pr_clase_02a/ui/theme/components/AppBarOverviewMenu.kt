package edu.victoraracil.pr_clase_02a.ui.theme.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AppBarOverviewMenu(
    onSorted: () -> Unit,
    onDeleteAll: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "Menú")
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { }
    ) {
        DropdownMenuItem(
            text = { Text("Ordenar por título") },
            onClick = {
                onSorted()
            }
        )
        DropdownMenuItem(
            text = { Text("Eliminar todos") },
            onClick = {
                onDeleteAll()
            }
        )
    }
}