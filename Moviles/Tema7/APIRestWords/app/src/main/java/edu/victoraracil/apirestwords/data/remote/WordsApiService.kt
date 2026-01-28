package edu.victoraracil.apirestwords.data.remote

import edu.victoraracil.apirestwords.data.model.Word
import retrofit2.Response
import retrofit2.http.GET

interface WordsApiService {

    @GET("all")
    suspend fun getAllWords(): Response<List<Word>>
}
