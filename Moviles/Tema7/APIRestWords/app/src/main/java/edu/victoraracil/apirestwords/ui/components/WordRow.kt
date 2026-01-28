package edu.victoraracil.apirestwords.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import edu.victoraracil.apirestwords.data.model.Word

@Composable
fun WordRow(
    word: Word, onClick: () -> Unit, onFavClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },

        headlineContent = {
            Text(text = word.word)
        },

        trailingContent = {
            IconButton(onClick = onFavClick) {
                Icon(
                    imageVector = if (word.favorite) Icons.Filled.Favorite
                    else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = if (word.favorite) Color.Red else Color.Black
                )
            }
        })
}
