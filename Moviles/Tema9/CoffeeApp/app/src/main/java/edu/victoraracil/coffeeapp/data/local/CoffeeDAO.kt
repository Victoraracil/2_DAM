package edu.victoraracil.coffeeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.victoraracil.coffeeapp.domain.model.Coffee
import edu.victoraracil.coffeeapp.domain.model.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeDao {


    @Query("SELECT * FROM coffees")
    fun getAllCoffees(): Flow<List<Coffee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffees(coffees: List<Coffee>)

    @Query("DELETE FROM coffees")
    suspend fun clearCoffees()

    @Query("SELECT * FROM coffees WHERE id = :id LIMIT 1")
    suspend fun getCoffeeById(id: Int): Coffee?

    @Query("SELECT * FROM comments WHERE idCoffee = :coffeeId")
    fun getCommentsForCoffee(coffeeId: Int): Flow<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<Comment>)

    @Query("DELETE FROM comments WHERE idCoffee = :coffeeId")
    suspend fun clearCommentsForCoffee(coffeeId: Int)
}
