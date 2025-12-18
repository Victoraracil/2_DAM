package edu.victoraracil.pr_clase_06a.data

import edu.victoraracil.pr_clase_06a.data.model.Brand
import edu.victoraracil.pr_clase_06a.data.model.Car
import edu.victoraracil.pr_clase_06a.data.model.CarWithBrand
import kotlinx.coroutines.flow.Flow

class LocalDatasource(private val dao: CarsDao) {

    val currentCar: Flow<List<CarWithBrand>> = dao.getCarsWithBrands()
    val currentBrand: Flow<List<Brand>> = dao.getAllBrands()

    suspend fun deleteCar(car: Car): Int { // Returns the number of rows deleted.
        return dao.deleteCar(car)
    }

    suspend fun saveCar(car: Car) {
        dao.insertCar(car)
    }

    suspend fun getCarById(carId: Int): Flow<Car> = dao.getCarById(carId) as Flow<Car>

    suspend fun saveBrand(brand: Brand) {
        dao.insertBrand(brand)
    }

    suspend fun getBrById(brandId: Int): Brand? = dao.getBrandById(brandId)
}
