package edu.victoraracil.notespmdm.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import edu.victoraracil.notespmdm.R
import edu.victoraracil.notespmdm.data.repository.SortOrder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesTopAppBar(
    currentSortOrder: SortOrder, onSortChange: (SortOrder) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(title = {
        Text(stringResource(R.string.txt_titleNotes))
    }, actions = {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.txt_menu)
            )
        }

        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {

            if (currentSortOrder == SortOrder.A_Z) {
                DropdownMenuItem(text = {
                    Text(stringResource(R.string.txt_onSortZA))
                }, onClick = {
                    expanded = false
                    onSortChange(SortOrder.Z_A)
                })
            } else {
                DropdownMenuItem(text = {
                    Text(stringResource(R.string.txt_onSortAZ))
                }, onClick = {
                    expanded = false
                    onSortChange(SortOrder.A_Z)
                })
            }
        }
    })
}
