package edu.victoraracil.filmlist.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.victoraracil.filmlist.data.model.Film


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmItem(
    film: Film, onLongClick: () -> Unit, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = onClick, onLongClick = onLongClick
            )
    ) {
        Row(modifier = Modifier.padding(8.dp)) {

            AsyncImage(
                model = film.cover, contentDescription = film.title, modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = film.title)
                Text(text = film.director)
                Text(text = "${film.year}")
                Text(text = "${film.duration} min")
                Text(text = film.genre)
            }
        }
    }
}
