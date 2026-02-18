package edu.practico.seriesapp.data.remote

import edu.practico.seriesapp.data.model.Character
import edu.practico.seriesapp.data.model.Show
import retrofit2.Response

class RemoteDataSource {

    private val apiService = RetrofitClient.apiService

    // Función para obtener el login
    fun getShowDetailById(id: Int): Show {
        val response = apiService.getShowDetailById(id)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Respuesta sin cuerpo")
        } else {
            throw Exception("Error al obtener el detalle del show con id $id: ${response.message()}")
        }
    }

    suspend fun getCharacters(): Response<List<Character>> {
        return apiService.getCharacters()
    }


}