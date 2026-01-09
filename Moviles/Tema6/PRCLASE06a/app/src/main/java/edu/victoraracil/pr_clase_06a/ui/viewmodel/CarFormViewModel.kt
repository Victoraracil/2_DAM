package edu.victoraracil.pr_clase_06a.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CarFormViewModel : ViewModel() {

    var model by mutableStateOf("")
        private set

    var motor by mutableStateOf("")
        private set

    var year by mutableStateOf("")
        private set

    var selectedBrandId by mutableStateOf<Int?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onModelChange(value: String) {
        model = value
    }

    fun onMotorChange(value: String) {
        motor = value
    }

    fun onYearChange(value: String) {
        year = value
    }

    fun onBrandSelected(id: Int) {
        selectedBrandId = id
    }

    fun validate(): Boolean {
        if (model.isBlank() || motor.isBlank() || year.isBlank() || selectedBrandId == null) {
            errorMessage = "Todos los campos son obligatorios"
            return false
        }

        val yearInt = year.toIntOrNull()
        if (yearInt == null || yearInt <= 0) {
            errorMessage = "El año no es válido"
            return false
        }

        errorMessage = null
        return true
    }
}
