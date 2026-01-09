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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    //Coches con su marca
    private val _cars = MutableStateFlow<List<CarWithBrand>>(emptyList())
    val cars: StateFlow<List<CarWithBrand>> = _cars

    //Marcas
    private val _brands = MutableStateFlow<List<Brand>>(emptyList())
    val brands: StateFlow<List<Brand>> = _brands

    init {
        val database = CarsDatabase.getDatabase(application)
        val dao = database.carsDao()
        val localDatasource = LocalDatasource(dao)
        repository = Repository(localDatasource)

        observeCars()
        observeBrands()
    }

    private fun observeCars() {
        viewModelScope.launch {
            repository.currentCars.collect {
                _cars.value = it
            }
        }
    }

    private fun observeBrands() {
        viewModelScope.launch {
            repository.currentBrands.collect {
                _brands.value = it
            }
        }
    }

    fun addCar(car: Car) {
        viewModelScope.launch {
            repository.saveCar(car)
        }
    }

    fun updateCar(car: Car) {
        viewModelScope.launch {
            repository.saveCar(car)
        }
    }

    fun deleteCar(car: Car) {
        viewModelScope.launch {
            repository.deleteCar(car)
        }
    }

    fun addBrand(name: String) {
        viewModelScope.launch {

            // Evitar duplicados
            val exists = brands.value.any {
                it.name.equals(name, ignoreCase = true)
            }

            if (!exists) {
                repository.saveBrand(
                    Brand(name = name)
                )
            }
        }
    }


    fun insertarDatosPrueba() {
        viewModelScope.launch {
            repository.saveBrand(Brand(name = "Toyota"))
            repository.saveBrand(Brand(name = "Ford"))
            repository.saveCar(Car(model = "Corolla", motor = "Hybrid", year = 2022, idBrand = 1))
            repository.saveCar(Car(model = "Focus", motor = "Diesel", year = 2020, idBrand = 2))
        }
    }

}
