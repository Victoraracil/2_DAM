package edu.victoraracil.coffeeapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.victoraracil.coffeeapp.R
import edu.victoraracil.coffeeapp.domain.model.Comment
import edu.victoraracil.coffeeapp.viewmodel.MainViewModel

@Composable
fun CoffeeDetailScreen(
    coffeeId: Int, onBack: () -> Unit, viewModel: MainViewModel = viewModel()
) {

    val context = LocalContext.current


    var author by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }

    val coffee by viewModel.selectedCoffee.collectAsState()
    val comments by viewModel.commentsState.collectAsState()
    val loadingComments by viewModel.loadingComments.collectAsState()

    LaunchedEffect(coffeeId) {
        viewModel.loadCoffeeDetail(coffeeId)
        viewModel.loadComments(coffeeId)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

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
            }) {
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
