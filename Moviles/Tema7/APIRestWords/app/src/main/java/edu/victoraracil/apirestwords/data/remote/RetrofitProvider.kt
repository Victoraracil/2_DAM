package edu.victoraracil.apirestwords.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private const val BASE_URL = "https://www.javiercarrasco.es/api/words/"

    val apiService: WordsApiService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(WordsApiService::class.java)
    }
}
