package edu.victoraracil.coffeeapp.data.repository

import edu.victoraracil.coffeeapp.data.local.LocalDataSource
import edu.victoraracil.coffeeapp.data.remote.RemoteDataSource
import edu.victoraracil.coffeeapp.domain.model.Coffee
import edu.victoraracil.coffeeapp.domain.model.Comment
import edu.victoraracil.coffeeapp.domain.model.LoginRequest
import kotlinx.coroutines.flow.first

class CoffeeRepositoryImpl(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) {

    suspend fun login(request: LoginRequest): String {
        val response = remote.login(request)

        if (response.isSuccessful) {
            return response.body()?.token
                ?: throw Exception("Token nulo")
        } else {
            throw Exception("Login incorrecto")
        }
    }


    suspend fun getCoffees(token: String): List<Coffee> {

        return try {
            val response = remote.getCoffees(token)

            if (response.isSuccessful) {
                val coffees = response.body() ?: emptyList()

                // Guardamos en Room (caché)
                local.saveCoffeesToCache(coffees)

                coffees
            } else {
                // Si falla la red, intentamos tirar de caché
                local.getAllCoffeesFromCache()
                    .first()
            }

        } catch (e: Exception) {
            // Si hay error de red, usamos caché
            local.getAllCoffeesFromCache()
                .first()
        }
    }


    suspend fun getCoffeeById(token: String, id: Int): Coffee? {

        return try {
            val response = remote.getCoffeeById(token, id)

            if (response.isSuccessful) {
                response.body()
            } else {
                // Si falla la red, tiramos de Room
                local.getCoffeeByIdFromCache(id)
            }
        } catch (e: Exception) {
            local.getCoffeeByIdFromCache(id)
        }
    }


    suspend fun getComments(token: String, coffeeId: Int): List<Comment> {

        return try {
            val response = remote.getComments(token, coffeeId)

            if (response.isSuccessful) {
                val comments = response.body() ?: emptyList()

                // Guardamos en caché
                local.saveCommentsForCoffee(coffeeId, comments)

                comments
            } else {
                // Si falla API, usamos Room
                local.getCommentsForCoffeeFromCache(coffeeId)
                    .first()
            }
        } catch (e: Exception) {
            local.getCommentsForCoffeeFromCache(coffeeId)
                .first()
        }
    }

    suspend fun postComment(
        token: String,
        coffeeId: Int,
        author: String,
        text: String
    ) {
        remote.postComment(token, coffeeId, author, text)

        //Después de publicar, refrescamos comentarios desde API
        val updatedComments = getComments(token, coffeeId)
        local.saveCommentsForCoffee(coffeeId, updatedComments)
    }
}
