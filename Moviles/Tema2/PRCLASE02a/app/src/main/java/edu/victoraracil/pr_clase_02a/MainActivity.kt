package edu.victoraracil.pr_clase_02a

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import edu.victoraracil.pr_clase_02a.data.model.ItemData
import edu.victoraracil.pr_clase_02a.data.model.ItemData.Companion.itemIdCounter
import edu.victoraracil.pr_clase_02a.ui.theme.PRCLASE02aTheme
import edu.victoraracil.pr_clase_02a.ui.theme.components.AppBarOverviewMenu
import edu.victoraracil.pr_clase_02a.ui.theme.components.ItemTarjeta
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRCLASE02aTheme {

                val context = LocalContext.current
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                val itemDataListState = rememberSaveable(
                    saver = listSaver(
                        save = { it.map { item -> "${item.id}|${item.title}|${item.description}|${item.imageUrl}|${item.isFavorite}" } },
                        restore = { savedList ->
                            savedList.map { line ->
                                val parts = line.split("|")
                                ItemData(
                                    id = parts[0].toInt(),
                                    title = parts[1],
                                    description = parts[2],
                                    imageUrl = parts[3],
                                    isFavorite = parts[4].toBoolean()
                                )
                            }.toMutableStateList()
                        }
                    )) {
                    mutableStateListOf(
                        ItemData(
                            ++itemIdCounter,
                            "Item $itemIdCounter",
                            "Descripción $itemIdCounter",
                            "https://picsum.photos/seed/$itemIdCounter/200/200"
                        ),
                        ItemData(
                            ++itemIdCounter,
                            "Item $itemIdCounter",
                            "Descripción $itemIdCounter",
                            "https://picsum.photos/seed/$itemIdCounter/200/200"
                        ),
                        ItemData(
                            ++itemIdCounter,
                            "Item $itemIdCounter",
                            "Descripción $itemIdCounter",
                            "https://picsum.photos/seed/$itemIdCounter/200/200"
                        )
                    )
                }

                var sortAscending by rememberSaveable { mutableStateOf(true) }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(id = R.string.app_name)) },
                            actions = {
                                AppBarOverviewMenu(
                                    onSorted = {
                                        sortAscending = !sortAscending
                                        val sorted = if (sortAscending)
                                            itemDataListState.sortedBy { it.title }
                                        else
                                            itemDataListState.sortedByDescending { it.title }

                                        itemDataListState.clear()
                                        itemDataListState.addAll(sorted)
                                    },
                                    onDeleteAll = {
                                        itemDataListState.clear()
                                    }
                                )
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            itemDataListState.add(
                                ItemData(
                                    ++itemIdCounter,
                                    "Item $itemIdCounter",
                                    "Descripción $itemIdCounter",
                                    "https://picsum.photos/seed/$itemIdCounter/200/200"
                                )
                            )
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Añadir")
                        }
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    ListaPantalla(
                        items = itemDataListState,
                        onClick = { item ->
                            Toast.makeText(
                                context,
                                "ID: ${item.id} - ${item.title}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onLongClick = { item ->
                            itemDataListState.remove(item)
                            scope.launch {
                                snackbarHostState.showSnackbar("Elemento eliminado: ${item.id} - ${item.title}")
                            }
                        },
                        onFavoriteClick = { clicked ->
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
    onClick: (ItemData) -> Unit,
    onLongClick: (ItemData) -> Unit,
    onFavoriteClick: (ItemData) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items, key = { it.id }) { item ->
            ItemTarjeta(
                item = item,
                onClick = onClick,
                onLongClick = onLongClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}