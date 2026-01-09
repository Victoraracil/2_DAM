package edu.victoraracil.tema7test.data.remote

import edu.victoraracil.tema7test.data.RetrofitClient

// RemoteDatasource.kt
class RemoteDatasource {
    // Servicio API utilizando Retrofit.
    private val apiService = RetrofitClient.apiService

    // Funciones para obtener datos desde la API.
    suspend fun getPosts() = apiService.getPosts()

    // Obtener un post por su ID.
    suspend fun getPostById(id: Int) = apiService.getPostById(id)
}