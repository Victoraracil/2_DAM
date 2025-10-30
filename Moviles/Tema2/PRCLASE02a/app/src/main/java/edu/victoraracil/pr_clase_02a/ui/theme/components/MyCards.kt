package edu.victoraracil.pr_clase_02a.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.victoraracil.pr_clase_02a.MainActivity.Item
import edu.victoraracil.pr_clase_02a.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemTarjeta(
    item: Item,
    onToggleFavorite: (Item) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isFavorite){
                Color(0xFF_00FF00)
            }else{
                Color(0xFF_8F8F8F)
            },
            contentColor = if (item.isFavorite){
                Color(0xFF_FF0000)
            }else{
                Color(0xFF_000000)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically


        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Imagen del producto",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text(text = item.subtitle, fontSize = 14.sp, color = Color.DarkGray)
            }

            IconButton(onClick = { onToggleFavorite(item) }) {
                if (item.isFavorite) {
                    Icon(Icons.Default.Favorite, contentDescription = "Favorito", tint = Color.Red)
                } else {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "No favorito")
                }
            }
        }
    }
}