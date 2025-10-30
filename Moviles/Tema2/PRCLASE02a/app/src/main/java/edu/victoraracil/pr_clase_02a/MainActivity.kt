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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.copy
import edu.victoraracil.pr_clase_02a.data.model.Item
import edu.victoraracil.pr_clase_02a.ui.theme.PRCLASE02aTheme
import edu.victoraracil.pr_clase_02a.ui.theme.components.ItemTarjeta

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
                            Item(1, "Producto 1", "Descripción 1", "https://picsum.photos/seed/$itemIdCounter/200/200"),
                            Item(2, "Producto 2", "Descripción 2", "https://picsum.photos/seed/$itemIdCounter/200/200"),
                            Item(3, "Producto 3", "Descripción 3","https://picsum.photos/seed/$itemIdCounter/200/200")
                        ),
                        modifier = Modifier.padding(innerPadding)
                    )
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
