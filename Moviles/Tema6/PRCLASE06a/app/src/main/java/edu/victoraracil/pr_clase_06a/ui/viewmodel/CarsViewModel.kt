package edu.victoraracil.pr_clase_06a.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.victoraracil.pr_clase_06a.data.CarsDatabase
import edu.victoraracil.pr_clase_06a.data.LocalDatasource
import edu.victoraracil.pr_clase_06a.data.Repository
import edu.victoraracil.pr_clase_06a.data.model.Brand
import edu.victoraracil.pr_clase_06a.data.model.Car
import edu.victoraracil.pr_clase_06a.data.model.CarWithBrand
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

// SupersViewModel.kt
class SupersViewModel(application: Application) : AndroidViewModel(application) {
    // Se inicializa el repositorio y el datasource.
    private val repository: Repository
    private val localDatasource: LocalDatasource

    // Se exponen los StateFlow para que la UI observe los cambios.
    private val _currentCars = MutableStateFlow<List<CarWithBrand>>(emptyList())
    val currentCars: StateFlow<List<CarWithBrand>> = _currentCars

    private val _currentBrand = MutableStateFlow<List<Brand>>(emptyList())
    val currentCarss: StateFlow<List<Brand>> = _currentBrand

    init {
        // Inicialización del repositorio y el datasource.
        val database = CarsDatabase.getDatabase(application)
        val dao = database.carsDao()
        localDatasource = LocalDatasource(dao)
        repository = Repository(localDatasource)

        // Carga inicial de superhéroes y editoriales, versión Flow.
        loadCars()
        loadBrands()

    }

    // Implementa funciones para interactuar con el repositorio.

    // Se observan los StateFlow para que la UI pueda reaccionar a los cambios con Flow una vez
    // que se hayan cargado los datos iniciales.
    fun loadBrands() {
        viewModelScope.launch {
            repository.currentBrands.catch { e -> e.printStackTrace() } // Manejo de errores.
                .collect { brands ->
                    _currentBrand.value = brands // Actualiza el StateFlow con las editoriales.
                }
        }
    }

    fun loadCars() {
        viewModelScope.launch {
            repository.currentCars.catch { e -> e.printStackTrace() } // Manejo de errores.
                .collect { cars ->
                    _currentCars.value = cars // Actualiza el StateFlow con los superhéroes.
                }
        }
    }

    fun saveBrand(brand: Brand) {
        viewModelScope.launch {
            repository.saveBrand(brand)
        }
    }

    fun saveCar(car: Car) {
        viewModelScope.launch {
            repository.saveCar(car)
        }
    }

    suspend fun delCar(car: Car): Int {
        return deleteCar(car).await()
    }

    // Esta función devuelve un Deferred para que se pueda esperar su resultado de forma asíncrona.
    private fun deleteCar(car: Car): Deferred<Int> {
        return viewModelScope.async {
            repository.deleteCar(car)
        }
    }

    fun getCarrById(carId: Int): Flow<Car> {
        return viewModelScope.async { repository.getCarById(carId) } as Flow<Car>
    }

    fun getBrandById(brandId: Int): Deferred<Brand?> {
        return viewModelScope.async { repository.getBrById(brandId) }
    }

}