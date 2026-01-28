package edu.victoraracil.apirestwords.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import edu.victoraracil.apirestwords.data.model.Word

@Composable
fun WordList(
    words: List<Word>, onWordClick: (Word) -> Unit, onFavClick: (Word) -> Unit
) {
    LazyColumn {
        items(words) { word ->
            WordRow(word = word, onClick = { onWordClick(word) }, onFavClick = { onFavClick(word) })
        }
    }
}
