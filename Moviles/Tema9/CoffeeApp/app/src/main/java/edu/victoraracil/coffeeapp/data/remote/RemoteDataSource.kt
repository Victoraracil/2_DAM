package edu.victoraracil.coffeeapp.data.remote

import edu.victoraracil.coffeeapp.domain.model.Coffee
import edu.victoraracil.coffeeapp.domain.model.Comment
import edu.victoraracil.coffeeapp.domain.model.LoginRequest
import edu.victoraracil.coffeeapp.domain.model.LoginResponse
import retrofit2.Response


/**
 * RemoteDataSource, class for Retrofit2 API.
 * @param api, Retrofit2 API interface.
 * @author Javier Carrasco
 */
class RemoteDataSource{
    private val TAG = RemoteDataSource::class.java.simpleName
    private val api = RetrofitClient.apiService

    // Función para obtener el login, se pasa el objeto RequestLogin en el body.
    // Se devuelve un objeto LoginResponse.


        suspend fun login(
            request: LoginRequest
        ): Response<LoginResponse> =
            api.login(request)

        suspend fun getCoffees(token: String): Response<List<Coffee>> =
            api.getCoffees("Bearer $token")

        suspend fun getCoffeeById(
            token: String,
            id: Int
        ): Response<Coffee> =
            api.getCoffeeById("Bearer $token", id)

        suspend fun getComments(
            token: String,
            coffeeId: Int
        ): Response<List<Comment>> =
            api.getComments("Bearer $token", coffeeId)

        suspend fun postComment(
            token: String,
            coffeeId: Int,
            author: String,
            text: String
        ): Response<Unit> {

            val body = mapOf(
                "coffee_id" to coffeeId,
                "author" to author,
                "comment" to text
            )

            return api.postComment("Bearer $token", body)
        }
}