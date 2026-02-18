package es.javiercarrasco.ejemplologin.data

import android.util.Log
import edu.javiercarrasco.coffeeapp.data.local.LocalDatasource
import edu.javiercarrasco.coffeeapp.data.model.Comment
import es.javiercarrasco.ejemplologin.data.model.Coffee
import es.javiercarrasco.ejemplologin.data.model.LoginRequest
import es.javiercarrasco.ejemplologin.data.model.LoginResponse
import es.javiercarrasco.ejemplologin.data.remote.RemoteDataSource

class Repository(val localDataSource: LocalDatasource) {
    private val remoteDataSource = RemoteDataSource()

    // Función para obtener el login.
    suspend fun login(request: LoginRequest): LoginResponse {
        return remoteDataSource.login(request)
    }

    // Función para obtener el café.
    suspend fun fetchCoffees(token: String): List<Coffee> {
        return try {
            val response = remoteDataSource.getCoffees(token)
            if (response.isSuccessful) {
                val coffeeList = response.body() ?: emptyList()
                if (coffeeList.isNotEmpty()) {
                    localDataSource.insertCoffees(coffeeList)
                }

                coffeeList
            } else {
                Log.e(
                    "Repository", "Error fetching coffees: ${response.code()} ${response.message()}"
                )
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("Repository", e.message, e)
            throw e // Lanzar la excepción para que el ViewModel pueda manejarla.
        }
    }

    suspend fun fetchCoffeeDetail(token: String, coffeeId: Int): Coffee? {
        return try {
            val response = remoteDataSource.getCoffeeDetail(token, coffeeId)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e(
                    "Repository",
                    "Error fetching coffee detail: ${response.code()} ${response.message()}"
                )
                null
            }
        } catch (e: Exception) {
            Log.e("Repository", e.message, e)
            throw e // Lanzar la excepción para que el ViewModel pueda manejarla.
        }
    }

    suspend fun fetchCommentsByCoffeeId(token: String, coffeeId: Int): List<Comment> {
        return try {
            val response = remoteDataSource.getCommentsByCoffeeId(token, coffeeId)
            if (response.isSuccessful) response.body() ?: emptyList()
            else {
                Log.e(
                    "Repository",
                    "Error fetching comments: ${response.code()} ${response.message()}"
                )
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("Repository", e.message, e)
            throw e // Lanzar la excepción para que el ViewModel pueda manejarla.
        }
    }

    // Función para añadir un comentario.
    suspend fun putComment(token: String, comment: Comment): Comment? {
        try {
            return remoteDataSource.putComment(token, comment)
        } catch (e: Exception) {
            Log.e("Repository", "putComment: ${e.message}")
            return null // Devuelve null si hay algún error en la operación.
        }
    }

    suspend fun preloadDescriptions(token: String) {
        val coffees = localDataSource.getAllCoffeesOnce()

        coffees.filter { it.coffeeDesc == null }.forEach { coffee ->

            val detail = fetchCoffeeDetail(token, coffee.id ?: return@forEach)

            detail?.coffeeDesc?.let { desc ->
                coffee.coffeeDesc = desc
                localDataSource.updateCoffee(coffee)
            }
        }
    }


}