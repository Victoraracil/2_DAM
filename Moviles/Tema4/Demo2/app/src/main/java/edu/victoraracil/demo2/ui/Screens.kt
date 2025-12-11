package edu.victoraracil.demo2.ui

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.victoraracil.demo2.R
import edu.victoraracil.demo2.model.Recipe

@Composable
fun ListScreen(
    recipes: List<Recipe>,
    onItemClick: (Recipe) -> Unit,
    onDeleteLongPressed: @Composable (Recipe) -> Unit,
    onDeleteFromMenu: @Composable (Recipe) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(8.dp)) {
        items(recipes) { recipe ->
            RecipeListItem(
                recipe = recipe,
                onItemClick = onItemClick,
                onDeleteLongPressed = onDeleteLongPressed as (Recipe) -> Unit,
                onDeleteFromMenu = onDeleteFromMenu as (Recipe) -> Unit
            )
        }
    }
}

@Composable
fun RecipeListItem(
    recipe: Recipe,
    onItemClick: (Recipe) -> Unit,
    onDeleteLongPressed: (Recipe) -> Unit,
    onDeleteFromMenu: (Recipe) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .combinedClickable(
                onClick = { onItemClick(recipe) },
                onLongClick = { onDeleteLongPressed(recipe) })
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = recipe.imageUrl, contentDescription = null, modifier = Modifier.size(80.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(recipe.name, style = MaterialTheme.typography.titleMedium)
                Text(recipe.description, style = MaterialTheme.typography.bodyMedium)
            }

            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.menu_more_options)
                    )
                }
                DropdownMenu(
                    expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.menu_delete_recipe)) },
                        onClick = {
                            expanded = false
                            onDeleteFromMenu(recipe)
                        })
                }
            }
        }
    }
}

@Composable
fun AddRecipeScreen(onRecipeAdded: (String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(stringResource(R.string.screen_add_recipe), style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.label_recipe_name)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(R.string.label_recipe_description)) },
            maxLines = 4,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { onRecipeAdded(name, description) }, // <- aquí funciona bien ahora
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(R.string.button_add_recipe))
        }
    }
}


@Composable
fun DetailScreen(recipe: Recipe?, onDeleteClicked: @Composable () -> Unit) {
    if (recipe == null) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(stringResource(R.string.message_no_recipe_selected))
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(recipe.name, style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(8.dp))

        AsyncImage(
            model = recipe.imageUrl.replace("/200/200", "/800/600"),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Text(recipe.description)

        Spacer(Modifier.height(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
            onClick = onDeleteClicked as () -> Unit
        ) {
            Text(stringResource(R.string.button_confirm))
        }
    }
}


