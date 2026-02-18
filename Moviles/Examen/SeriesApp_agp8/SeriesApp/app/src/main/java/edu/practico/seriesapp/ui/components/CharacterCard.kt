package edu.practico.seriesapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import edu.practico.seriesapp.data.model.Character

@Composable
fun CharacterCard(
    character: Character, onCharacterClick: (Int) -> Unit
) {
    val hasShow = character.idShow != null

    OutlinedCard(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(character.imageUrl)
                    .crossfade(true).build(),
                contentDescription = character.name,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 12.dp)
            )

            TextButton(
                enabled = hasShow, onClick = {
                    onCharacterClick(character.idShow!!)
                }) {
                Text(
                    text = if (hasShow) "Ver actor" else "Actor no disponible",
                    color = if (hasShow) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }

        }
    }
}