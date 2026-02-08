package edu.victoraracil.coffeeapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.victoraracil.coffeeapp.data.local.CoffeeDatabase
import edu.victoraracil.coffeeapp.data.local.LocalDataSource
import edu.victoraracil.coffeeapp.data.local.SessionManager
import edu.victoraracil.coffeeapp.data.local.dataStore
import edu.victoraracil.coffeeapp.data.remote.RemoteDataSource
import edu.victoraracil.coffeeapp.data.repository.CoffeeRepositoryImpl
import edu.victoraracil.coffeeapp.domain.model.Coffee
import edu.victoraracil.coffeeapp.domain.model.Comment
import edu.victoraracil.coffeeapp.domain.model.LoginRequest
import edu.victoraracil.coffeeapp.domain.model.LoginResponse
import edu.victoraracil.coffeeapp.domain.model.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager: SessionManager
    private val repository: CoffeeRepositoryImpl
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
    private val _coffeeState = MutableStateFlow<List<Coffee>>(emptyList())
    val coffeeState: StateFlow<List<Coffee>> = _coffeeState.asStateFlow()
    private val _selectedCoffee = MutableStateFlow<Coffee?>(null)
    val selectedCoffee: StateFlow<Coffee?> = _selectedCoffee.asStateFlow()
    private val _commentsState = MutableStateFlow<List<Comment>>(emptyList())
    val commentsState: StateFlow<List<Comment>> = _commentsState.asStateFlow()
    private val _loadingComments = MutableStateFlow(false)
    val loadingComments: StateFlow<Boolean> = _loadingComments.asStateFlow()

    init {
        val dataStore: DataStore<Preferences> = application.dataStore
        sessionManager = SessionManager(dataStore)

        val dao = CoffeeDatabase.getDatabase(application).coffeeDao()
        val localDataSource = LocalDataSource(dao)
        val remoteDataSource = RemoteDataSource()

        repository = CoffeeRepositoryImpl(
            remote = remoteDataSource, local = localDataSource
        )
    }


    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val token = repository.login(request)
                sessionManager.saveSession(token)

                _loginState.value = LoginState.Success(
                    LoginResponse(
                        ok = true, token = token, username = "", message = "Sesión restaurada"
                    )
                )

            } catch (e: Exception) {
                Log.e("MainViewModel", "Error durante el login", e)
                _loginState.value = LoginState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun checkSessionOnStart() {
        viewModelScope.launch {
            sessionManager.tokenFlow.collect { token ->
                if (token == null) {
                    _loginState.value = LoginState.Idle
                } else {
                    _loginState.value = LoginState.Success(
                        LoginResponse(
                            ok = true, token = token, username = "", message = "Sesión restaurada"
                        )
                    )
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _loginState.value = LoginState.Idle
        }
    }


    fun loadCoffees() {
        viewModelScope.launch {
            val token = sessionManager.tokenFlow.first() ?: return@launch

            try {
                val coffees = repository.getCoffees(token)
                _coffeeState.value = coffees
                Log.d("MainViewModel", "Cafés cargados: ${coffees.size}")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error cargando cafés", e)
            }
        }
    }


    fun loadCoffeeDetail(id: Int) {
        viewModelScope.launch {
            val token = sessionManager.tokenFlow.first() ?: return@launch

            try {
                val coffee = repository.getCoffeeById(token, id)
                _selectedCoffee.value = coffee
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error cargando detalle", e)
            }
        }
    }


    fun loadComments(coffeeId: Int) {
        viewModelScope.launch {
            _loadingComments.value = true

            val token = sessionManager.tokenFlow.first() ?: run {
                _loadingComments.value = false
                return@launch
            }

            try {
                val list = repository.getComments(token, coffeeId)
                _commentsState.value = list
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error cargando comentarios", e)
            }

            _loadingComments.value = false
        }
    }


    fun postComment(coffeeId: Int, author: String, text: String) {
        viewModelScope.launch {
            val token = sessionManager.tokenFlow.first() ?: return@launch

            try {
                repository.postComment(token, coffeeId, author, text)
                _commentsState.value = emptyList()
                loadComments(coffeeId)

            } catch (e: Exception) {
                Log.e("MainViewModel", "Error enviando comentario", e)
            }
        }
    }

}
