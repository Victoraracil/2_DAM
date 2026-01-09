package edu.victoraracil.tema7test.data.local

import edu.victoraracil.tema7test.data.model.Post
import kotlinx.coroutines.flow.Flow

// LocalDatasource.kt
class LocalDatasource(private val dao: PostsDAO) {

    // Obtiene todos los posts desde la base de datos local.
    fun getPosts(): Flow<List<Post>> = dao.getPosts()

    // Inserta una lista de posts en la base de datos local.
    suspend fun insertAllPosts(posts: List<Post>) {
        dao.insertAllPosts(posts)
    }
}