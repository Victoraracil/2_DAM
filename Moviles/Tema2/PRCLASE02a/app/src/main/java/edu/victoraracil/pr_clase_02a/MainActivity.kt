package edu.victoraracil.pr_clase_02a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.victoraracil.pr_clase_02a.ui.theme.PRCLASE02aTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRCLASE02aTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Lista de Productos") })
                    }
                ) { innerPadding ->
                    ListaPantalla(
                        itemsIniciales = listOf(
                            Item(1, "Producto 1", "Descripción 1", R.drawable.img),
                            Item(2, "Producto 2", "Descripción 2", R.drawable.img),
                            Item(3, "Producto 3", "Descripción 3", R.drawable.img)
                        ),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    data class Item(
        val id: Int,
        val title: String,
        val subtitle: String,
        val imagen: Int,
        var isFavorite: Boolean = false
    )

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

    @Composable
    fun ListaPantalla(itemsIniciales: List<Item>, modifier: Modifier = Modifier) {
        val items = remember { mutableStateListOf<Item>().apply { addAll(itemsIniciales) } }

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(items) { item ->
                ItemTarjeta(
                    item = item,
                    onToggleFavorite = { clicked ->
                        val index = items.indexOf(clicked)
                        items[index] = clicked.copy(isFavorite = !clicked.isFavorite)
                    }
                )
            }
        }
    }
}
