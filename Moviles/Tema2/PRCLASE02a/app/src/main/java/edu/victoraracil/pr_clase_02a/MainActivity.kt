package edu.victoraracil.pr_clase_02a

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.victoraracil.pr_clase_02a.data.model.ItemData
import edu.victoraracil.pr_clase_02a.data.model.ItemData.Companion.itemIdCounter
import edu.victoraracil.pr_clase_02a.ui.theme.PRCLASE02aTheme
import edu.victoraracil.pr_clase_02a.ui.theme.components.ItemTarjeta

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRCLASE02aTheme {
                val itemDataListState = remember {
                    mutableStateListOf(
                        ItemData(++itemIdCounter, "Item $itemIdCounter", "Descripción del item $itemIdCounter", "https://picsum.photos/seed/$itemIdCounter/200/200"),
                        ItemData(++itemIdCounter, "Item $itemIdCounter", "Descripción del item $itemIdCounter", "https://picsum.photos/seed/$itemIdCounter/200/200"),
                        ItemData(++itemIdCounter, "Item $itemIdCounter", "Descripción del item $itemIdCounter", "https://picsum.photos/seed/$itemIdCounter/200/200")
                    )
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(id = R.string.app_name)) }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            itemDataListState.add(
                                ItemData(
                                    ++itemIdCounter,
                                    "Item $itemIdCounter",
                                    "Descripción del item $itemIdCounter",
                                    "https://picsum.photos/seed/$itemIdCounter/200/200"
                                )
                            )
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Añadir elemento")
                        }
                    }
                ) { innerPadding ->
                    ListaPantalla(
                        items = itemDataListState,
                        onToggleFavorite = { clicked ->
                            val index = itemDataListState.indexOf(clicked)
                            if (index != -1) {
                                val actualizado = clicked.copy(isFavorite = !clicked.isFavorite)
                                itemDataListState[index] = actualizado
                            }
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ListaPantalla(
    items: List<ItemData>,
    onToggleFavorite: (ItemData) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 8.dp)
    ) {
        for (item in items) {
            ItemTarjeta(item = item, onToggleFavorite = onToggleFavorite)
        }
    }
}