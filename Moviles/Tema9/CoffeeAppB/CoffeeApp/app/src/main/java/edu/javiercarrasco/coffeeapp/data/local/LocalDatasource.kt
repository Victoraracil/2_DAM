package edu.javiercarrasco.coffeeapp.data.local

import es.javiercarrasco.ejemplologin.data.model.Coffee
import kotlinx.coroutines.flow.Flow

class LocalDatasource(val coffeesDao: CoffeesDao) {
    val coffees = coffeesDao.getAllCoffees()

    suspend fun insertCoffees(coffees: List<Coffee>) {
        coffeesDao.insertCoffees(coffees)
    }

    suspend fun updateCoffee(coffee: Coffee) {
        coffeesDao.updateCoffee(coffee)
    }

    fun getAllCoffees(): Flow<List<Coffee>> {
        return coffeesDao.getAllCoffees()
    }

    suspend fun getAllCoffeesOnce(): List<Coffee> {
        return coffeesDao.getAllCoffeesOnce()
    }

}