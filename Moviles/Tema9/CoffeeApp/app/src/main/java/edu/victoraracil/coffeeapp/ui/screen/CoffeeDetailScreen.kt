package edu.victoraracil.coffeeapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.victoraracil.coffeeapp.R
import edu.victoraracil.coffeeapp.domain.model.Comment
import edu.victoraracil.coffeeapp.viewmodel.MainViewModel

@Composable
fun CoffeeDetailScreen(
    coffeeId: Int,
    onBack: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {

    val context = LocalContext.current

    // Estados locales para el formulario de comentario
    var author by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }

    // Observamos datos desde el ViewModel
    val coffee by viewModel.selectedCoffee.collectAsState()
    val comments by viewModel.commentsState.collectAsState()
    val loadingComments by viewModel.loadingComments.collectAsState()

    // Cargamos detalle y comentarios al entrar a la pantalla
    LaunchedEffect(coffeeId) {
        viewModel.loadCoffeeDetail(coffeeId)
        viewModel.loadComments(coffeeId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // BOTÓN VOLVER
        Button(onClick = onBack) {
            Text(text = context.getString(R.string.txt_back))
        }

        Spacer(modifier = Modifier.height(8.dp))


        coffee?.let {
            Text(text = it.coffeeName ?: "", modifier = Modifier.padding(bottom = 4.dp))
            Text(text = it.coffeeDesc ?: "", modifier = Modifier.padding(bottom = 8.dp))
        } ?: run {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = context.getString(R.string.txt_comments_title))

        if (loadingComments) {
            CircularProgressIndicator()
        } else if (comments.isEmpty()) {
            Text(text = context.getString(R.string.txt_comments_empty))
        } else {
            LazyColumn {
                items(items = comments) { comment ->
                    CommentRow(comment = comment)
                }
            }

        }


        Spacer(modifier = Modifier.height(16.dp))

        Text(text = context.getString(R.string.txt_new_comment))

        OutlinedTextField(
            value = author,
            onValueChange = { author = it },
            label = { Text(context.getString(R.string.txt_author)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it },
            label = { Text(context.getString(R.string.txt_comment)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (author.isNotBlank() && commentText.isNotBlank()) {
                    viewModel.postComment(coffeeId, author, commentText)
                    author = ""
                    commentText = ""
                }
            }
        ) {
            Text(text = context.getString(R.string.txt_send_comment))
        }
    }
}

@Composable
fun CommentRow(comment: Comment) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(text = comment.author)
        Text(text = comment.comment)
    }
}
