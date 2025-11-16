package edu.victoraracil.demo1.ui

data class FormUiState(
    val name: String = "",
    val surname: String = "",
    val birthDateMillis: Long? = null,
    val hasLicense: Boolean = false,
    val eveningShift: Boolean = false, // Indica si el turno es de tarde
    val selectedModules: Set<String> = emptySet(), // IDs de módulos seleccionados
    val attemptedSubmit: Boolean = false, // Indica si se ha intentado enviar el formulario
    val isSubmissionSuccessful: Boolean = false,

    val snackbarMessage: String? = null, // Mensaje para Snackbar
    val showValidToast: Boolean = false, // Control para mostrar Toast "Formulario válido"
    val showSummaryCard: Boolean = false, // Mostrar tarjeta resumen

    val validateButtonEnabled: Boolean = true, // Botón Validar habilitado
    val resetButtonEnabled: Boolean = false
) {
    val isNameValid get() = name.isNotBlank()
    val isSurnameValid get() = surname.isNotBlank()
    val isBirthDateValid get() = birthDateMillis != null
    val isAtLeastOneModule get() = selectedModules.isNotEmpty()
    val isFormValid get() = isNameValid && isSurnameValid && isBirthDateValid && isAtLeastOneModule
}