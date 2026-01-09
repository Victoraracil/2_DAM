package edu.victoraracil.notespmdm.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopAppBar(
    onSortAsc: () -> Unit,
    onSortDesc: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("Notas") },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menú"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Ordenar A–Z") },
                    onClick = {
                        expanded = false
                        onSortAsc()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Ordenar Z–A") },
                    onClick = {
                        expanded = false
                        onSortDesc()
                    }
                )
            }
        }
    )
}
