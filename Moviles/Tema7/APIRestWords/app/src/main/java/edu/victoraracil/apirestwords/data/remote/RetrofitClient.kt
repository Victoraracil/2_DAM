package edu.victoraracil.apirestwords.data.remote

import edu.victoraracil.apirestwords.data.model.Word
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// RetrofitClient.kt
object RetrofitClient {
    private const val BASE_URL = "https://www.javiercarrasco.es/api/words/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

interface ApiService {
    @GET("words")
    suspend fun getAllWords(): Response<List<Word>>

    @GET("words/{id}")
    suspend fun getWordById(@Path("id") id: Int): Response<Word>
}