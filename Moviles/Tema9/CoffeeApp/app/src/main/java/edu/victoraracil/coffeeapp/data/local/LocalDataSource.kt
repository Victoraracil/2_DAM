package edu.victoraracil.coffeeapp.data.local

import edu.victoraracil.coffeeapp.domain.model.Coffee
import edu.victoraracil.coffeeapp.domain.model.Comment
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val coffeeDao: CoffeeDao) {


    fun getAllCoffeesFromCache(): Flow<List<Coffee>> {
        return coffeeDao.getAllCoffees()
    }

    suspend fun saveCoffeesToCache(coffees: List<Coffee>) {
        coffeeDao.clearCoffees()
        coffeeDao.insertCoffees(coffees)
    }

    suspend fun getCoffeeByIdFromCache(id: Int): Coffee? {
        return coffeeDao.getCoffeeById(id)
    }


    fun getCommentsForCoffeeFromCache(coffeeId: Int): Flow<List<Comment>> {
        return coffeeDao.getCommentsForCoffee(coffeeId)
    }

    suspend fun saveCommentsForCoffee(
        coffeeId: Int,
        comments: List<Comment>
    ) {
        coffeeDao.clearCommentsForCoffee(coffeeId)
        coffeeDao.insertComments(comments)
    }
}
