package edu.victoraracil.demo1.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormViewModel : ViewModel() {

    val _uiState = MutableStateFlow(FormUiState())
    val uiState = _uiState.asStateFlow()

    fun onNameChange(v: String) = _uiState.update { it.copy(name = v) }
    fun onSurnameChange(v: String) = _uiState.update { it.copy(surname = v) }
    fun onBirthDateChange(millis: Long?) = _uiState.update { it.copy(birthDateMillis = millis) }
    fun onLicenseChange(v: Boolean) = _uiState.update { it.copy(hasLicense = v) }
    fun onEveningShiftChange(v: Boolean) = _uiState.update { it.copy(eveningShift = v) }

    fun toggleModule(id: String, checked: Boolean) = _uiState.update {
        it.copy(selectedModules = if (checked) it.selectedModules + id else it.selectedModules - id)
    }

    fun validateForm() {
        _uiState.update { state ->
            when {
                state.name.isBlank() -> state.copy(
                    attemptedSubmit = true,
                    snackbarMessage = "El nombre no puede estar vacío"
                )

                state.surname.isBlank() -> state.copy(
                    attemptedSubmit = true,
                    snackbarMessage = "Los apellidos no pueden estar vacíos"
                )

                state.birthDateMillis == null -> state.copy(
                    attemptedSubmit = true,
                    snackbarMessage = "Debe seleccionar la fecha de nacimiento"
                )

                state.selectedModules.isEmpty() -> state.copy(
                    attemptedSubmit = true,
                    snackbarMessage = "Debe seleccionar al menos un módulo"
                )

                else -> state.copy(
                    attemptedSubmit = true,
                    isSubmissionSuccessful = true,
                    showValidToast = true,
                    showSummaryCard = true,
                    validateButtonEnabled = false,
                    resetButtonEnabled = true
                )
            }
        }
    }

    // Limpiar mensaje del Snackbar tras mostrarlo
    fun onSnackbarShown() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun resetForm() {
        _uiState.value = FormUiState()
    }

    fun onAttemptSubmit() {
        _uiState.update { it.copy(attemptedSubmit = true) }
    }
}