package edu.victoraracil.demo2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.victoraracil.demo2.ui.AddRecipeScreen
import edu.victoraracil.demo2.ui.DetailScreen
import edu.victoraracil.demo2.ui.ListScreen
import edu.victoraracil.demo2.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: RecipeViewModel = viewModel()
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.app_name)) }
                    )
                },
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = {
                    NavigationBar {
                        val currentRoute =
                            navController.currentBackStackEntryAsState().value?.destination?.route

                        NavigationBarItem(
                            selected = currentRoute == "list",
                            onClick = {
                                navController.navigate("list") {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Filled.List, stringResource(R.string.screen_recipes_list)) },
                            label = { Text(stringResource(R.string.screen_recipes_list)) }
                        )

                        NavigationBarItem(
                            selected = currentRoute == "add",
                            onClick = {
                                navController.navigate("add") {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Filled.Add, stringResource(R.string.screen_add_recipe)) },
                            label = { Text(stringResource(R.string.screen_add_recipe)) }
                        )
                    }
                }
            ) { padding ->

                // NAVIGATION HOST
                NavHost(
                    navController = navController,
                    startDestination = "list",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("list") {
                        ListScreen(
                            recipes = viewModel.recipes.collectAsState().value,
                            onItemClick = {
                                viewModel.selectRecipe(it)
                                navController.navigate("detail")
                            },
                            onDeleteLongPressed = { recipe ->
                                viewModel.selectRecipe(recipe)
                                viewModel.showDialog(
                                    title = getString(R.string.dialog_title_confirm_delete),
                                    message = getString(R.string.message_confirm_delete)
                                )
                            },
                            onDeleteFromMenu = { recipe ->
                                viewModel.selectRecipe(recipe)
                                viewModel.showDialog(
                                    title = getString(R.string.dialog_title_confirm_delete),
                                    message = getString(R.string.message_confirm_delete)
                                )
                            }
                        )
                    }

                    composable("add") {
                        AddRecipeScreen { name, desc ->
                            val success = viewModel.addRecipe(name, desc)

                            // Usamos getString() en lugar de stringResource()
                            val message = if (success)
                                this@MainActivity.getString(R.string.message_recipe_added)
                            else
                                this@MainActivity.getString(R.string.message_fill_all_fields)

                            scope.launch {
                                snackbarHostState.showSnackbar(message)
                            }

                            if (success) navController.navigate("list")
                        }
                    }

                    composable("detail") {
                        DetailScreen(
                            recipe = viewModel.selectedRecipe.collectAsState().value,
                            onDeleteClicked = {
                                viewModel.showDialog(
                                    title = getString(R.string.dialog_title_confirm_delete),
                                    message = getString(R.string.message_confirm_delete)
                                )
                            }
                        )
                    }
                }

                // DIALOGO DE CONFIRMACION
                val dialog = viewModel.dialogState.collectAsState().value
                if (dialog.visible) {
                    AlertDialog(
                        onDismissRequest = { viewModel.dismissDialog() },
                        title = { Text(dialog.title) },
                        text = { Text(dialog.message) },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.deleteSelectedRecipe()
                                viewModel.dismissDialog()
                                navController.popBackStack()
                            }) {
                                Text(getString(R.string.button_confirm))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { viewModel.dismissDialog() }) {
                                Text(getString(R.string.button_cancel))
                            }
                        }
                    )
                }
            }
        }
    }
}
