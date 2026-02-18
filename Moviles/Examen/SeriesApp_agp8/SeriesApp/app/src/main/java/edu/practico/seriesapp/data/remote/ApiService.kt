package edu.practico.seriesapp.data.remote

import edu.practico.seriesapp.data.model.Character
import edu.practico.seriesapp.data.model.Show
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("showdetails/{id}") //no necesita token
    fun getShowDetailById(@Path("id") id: Int): Response<Show>

    @GET("characters") //Lista de characters
    suspend fun getCharacters(): Response<List<Character>>
}