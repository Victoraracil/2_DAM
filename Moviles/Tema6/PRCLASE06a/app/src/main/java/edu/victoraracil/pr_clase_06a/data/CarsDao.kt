package edu.victoraracil.pr_clase_06a.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import edu.victoraracil.pr_clase_06a.data.model.Brand
import edu.victoraracil.pr_clase_06a.data.model.Car
import edu.victoraracil.pr_clase_06a.data.model.CarWithBrand
import kotlinx.coroutines.flow.Flow

@Dao
interface CarsDao {
    //Brand
    @Query("SELECT * FROM brands ORDER BY name ASC")
    fun getAllBrands(): Flow<List<Brand>>

    @Query("SELECT * FROM brands WHERE idBrand = :id LIMIT 1")
    fun getBrandById(id: Int): Brand?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBrand(brand: Brand)


    //Car
    @Transaction
    @Query("SELECT * FROM cars ORDER BY model ASC")
    fun getCarsWithBrands(): Flow<List<CarWithBrand>>

    @Query("SELECT * FROM cars WHERE idCar = :id LIMIT 1")
    fun getCarById(id: Int): Flow<Car?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car): Int
}
