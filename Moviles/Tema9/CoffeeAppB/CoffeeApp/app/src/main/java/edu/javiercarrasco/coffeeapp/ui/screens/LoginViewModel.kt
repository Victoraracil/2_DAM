package edu.javiercarrasco.coffeeapp.ui.screens

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.javiercarrasco.coffeeapp.data.local.AppDatabase
import edu.javiercarrasco.coffeeapp.data.local.LocalDatasource
import es.javiercarrasco.ejemplologin.data.Repository
import es.javiercarrasco.ejemplologin.data.model.LoginRequest
import es.javiercarrasco.ejemplologin.data.model.LoginResponse
import es.javiercarrasco.ejemplologin.data.model.LoginState
import es.javiercarrasco.ejemplologin.data.model.SessionManager
import es.javiercarrasco.ejemplologin.data.model.dataStore
import es.javiercarrasco.ejemplologin.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    private val localDatasource: LocalDatasource
    private val remoteDatasource: RemoteDataSource
    private val sessionManager: SessionManager

    // Estado del login.
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    init {
        val db = AppDatabase.getInstance(application)
        localDatasource = LocalDatasource(db.coffeesDao())
        remoteDatasource = RemoteDataSource()
        repository = Repository(localDatasource)

        val dataStore: DataStore<Preferences> = application.dataStore
        sessionManager = SessionManager(dataStore)
        getSessionFlow()
    }

    // Función para iniciar sesión y obtener el token.
    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                // Realiza la llamada al repositorio para iniciar sesión.
                val response = repository.login(request)

                // Guarda la sesión utilizando el SessionManager.
                sessionManager.saveSession(
                    response.token!!,
                    request.user
                ) // Guarda la sesión en el SessionManager.

                _loginState.value = LoginState.Success(response)
            } catch (e: Exception) {
                // Maneja cualquier error que ocurra durante el inicio de sesión.
                Log.e("MainViewModel", "Error durante el login", e)
                _loginState.value = LoginState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    // Función para obtener el flujo de la sesión.
    fun getSessionFlow() {
        viewModelScope.launch {
            sessionManager.sessionFlow.collect {
                Log.d(
                    "getSessionFlow",
                    "stateOptionLogin: Hola ${it.second}, your Token: ${it.first}"
                )
                if (it.first != null) {
                    // Si el token no es null, el usuario ha iniciado sesión y se puede
                    // recuperar el nombre de usuario y el token desde el flujo (DataStore).
                    _loginState.value = LoginState.Success(
                        LoginResponse(
                            ok = true,
                            token = it.first,
                            username = it.second!!,
                            message = "Already logged in"
                        )
                    )
                } else {
                    // Si el token es null, el usuario no ha iniciado sesión.
                    Log.d("getSessionFlow", "stateOptionLogin: No has iniciado sesión")
                    _loginState.value = LoginState.Idle // Estado inactivo
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _loginState.value = LoginState.Idle
            sessionManager.clearSession()
        }
    }
}