package edu.victoraracil.pr_clase_06a.data

import edu.victoraracil.pr_clase_06a.data.model.Brand
import edu.victoraracil.pr_clase_06a.data.model.Car
import edu.victoraracil.pr_clase_06a.data.model.CarWithBrand
import kotlinx.coroutines.flow.Flow

class Repository(private val localDatasource: LocalDatasource) {

    val currentCar: Flow<List<CarWithBrand>> = localDatasource.currentCar
    val currentBrand: Flow<List<Brand>> = localDatasource.currentBrand

    suspend fun deleteCar(car: Car): Unit { // Returns the number of rows deleted.
        return localDatasource.deleteCar(car)
    }

    suspend fun saveCar(car: Car) {
        localDatasource.saveCar(car)
    }

    suspend fun getCarById(carId: Int): Car? = localDatasource.getCarById(carId)

    suspend fun saveBrand(brand: Brand) {
        localDatasource.saveBrand(brand)
    }

    suspend fun getBrById(brandId: Brand): Flow<Brand> = localDatasource.getBrById(brandId)
}