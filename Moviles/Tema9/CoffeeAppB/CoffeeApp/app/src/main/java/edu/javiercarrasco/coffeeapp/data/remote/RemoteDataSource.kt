package es.javiercarrasco.ejemplologin.data.remote

import android.util.Log
import edu.javiercarrasco.coffeeapp.data.model.Comment
import es.javiercarrasco.ejemplologin.data.model.Coffee
import es.javiercarrasco.ejemplologin.data.model.LoginRequest
import es.javiercarrasco.ejemplologin.data.model.LoginResponse
import retrofit2.Response

/**
 * RemoteDataSource, class for Retrofit2 API.
 * @param api, Retrofit2 API interface.
 * @author Javier Carrasco
 */
class RemoteDataSource {
    private val TAG = RemoteDataSource::class.java.simpleName
    private val api = RetrofitClient.apiService

    // Función para obtener el login, se pasa el objeto RequestLogin en el body.
    // Se devuelve un objeto LoginResponse.
    suspend fun login(request: LoginRequest): LoginResponse {
        val response = api.login(request)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Respuesta vacía del servidor")
        } else {
            val errorBody = response.errorBody()?.string() // Se obtienen detalles del error.
            Log.e(TAG, "Error: ${response.message()} | $errorBody")
            throw Exception("Error en login: ${response.message()}")
        }
    }

    // Función para obtener el café.
    suspend fun getCoffees(token: String) = api.getCoffees("Bearer $token")

    suspend fun getCoffeeDetail(token: String, coffeeId: Int): Response<Coffee> {
        return api.getCoffeeDetail("Bearer $token", coffeeId)
    }

    suspend fun getCommentsByCoffeeId(token: String, coffeeId: Int) =
        api.getCommentsByCoffeeId("Bearer $token", coffeeId)

    // Función para añadir un comentario.
    suspend fun putComment(token: String, comment: Comment): Comment {
        val response = api.putComment("Bearer $token", comment)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Respuesta vacía del servidor")
        } else {
            val errorBody = response.errorBody()?.string() // Se obtienen detalles del error.
            Log.e(TAG, "Error: ${response.message()} | $errorBody")
            throw Exception("Error en putComment: ${response.message()}")
        }
    }
}