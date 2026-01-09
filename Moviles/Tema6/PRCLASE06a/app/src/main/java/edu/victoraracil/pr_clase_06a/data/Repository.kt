package edu.victoraracil.pr_clase_06a.data

import edu.victoraracil.pr_clase_06a.data.model.Brand
import edu.victoraracil.pr_clase_06a.data.model.Car
import edu.victoraracil.pr_clase_06a.data.model.CarWithBrand
import kotlinx.coroutines.flow.Flow

class Repository(private val localDatasource: LocalDatasource) {

    val currentCars: Flow<List<CarWithBrand>> = localDatasource.currentCar
    val currentBrands: Flow<List<Brand>> = localDatasource.currentBrand

    suspend fun deleteCar(car: Car): Int { //Returns the number of rows deleted.
        return localDatasource.deleteCar(car)
    }

    suspend fun saveCar(car: Car) {
        localDatasource.saveCar(car)
    }

    suspend fun getCarById(carId: Int): Flow<Car> = localDatasource.getCarById(carId)

    suspend fun saveBrand(brand: Brand) {
        localDatasource.saveBrand(brand)
    }

    suspend fun getBrById(brandId: Int): Brand? = localDatasource.getBrById(brandId)
}