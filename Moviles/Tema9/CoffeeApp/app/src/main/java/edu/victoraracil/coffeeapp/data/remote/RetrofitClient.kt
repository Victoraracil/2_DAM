package edu.victoraracil.coffeeapp.data.remote

import edu.victoraracil.coffeeapp.domain.model.Coffee
import edu.victoraracil.coffeeapp.domain.model.Comment
import edu.victoraracil.coffeeapp.domain.model.LoginRequest
import edu.victoraracil.coffeeapp.domain.model.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * RetrofitClient, object for Retrofit2 API.
 * ApiService, interface for Retrofit2 API.
 * @author Javier Carrasco
 */
object RetrofitClient {
    private val BASE_URL = "https://www.javiercarrasco.es/api/coffee/"

    val apiService: ApiService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}

interface ApiService {

    @POST("login")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("coffee")
    suspend fun getCoffees(
        @Header("Authorization") token: String
    ): Response<List<Coffee>>

    @GET("coffee/{id}")
    suspend fun getCoffeeById(
        @Header("Authorization") token: String, @Path("id") coffeeId: Int
    ): Response<Coffee>

    @GET("comments/{idCoffee}")
    suspend fun getComments(
        @Header("Authorization") token: String, @Path("idCoffee") coffeeId: Int
    ): Response<List<Comment>>

    @POST("comments")
    @Headers("Content-Type: application/json")
    suspend fun postComment(
        @Header("Authorization") token: String, @Body comment: Map<String, Any>
    ): Response<Unit>
}
