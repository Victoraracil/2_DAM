package edu.javiercarrasco.coffeeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.javiercarrasco.ejemplologin.data.model.Coffee
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffees(coffees: List<Coffee>)

    @Query("SELECT * FROM coffees")
    fun getAllCoffees(): Flow<List<Coffee>>

    @Query("SELECT * FROM coffees")
    suspend fun getAllCoffeesOnce(): List<Coffee>

    @Update
    suspend fun updateCoffee(coffee: Coffee)
}