package edu.javiercarrasco.coffeeapp.ui.screens

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.javiercarrasco.coffeeapp.data.local.AppDatabase
import edu.javiercarrasco.coffeeapp.data.local.LocalDatasource
import edu.javiercarrasco.coffeeapp.data.model.Comment
import es.javiercarrasco.ejemplologin.data.Repository
import es.javiercarrasco.ejemplologin.data.model.Coffee
import es.javiercarrasco.ejemplologin.data.model.LoginState
import es.javiercarrasco.ejemplologin.data.model.SessionManager
import es.javiercarrasco.ejemplologin.data.model.dataStore
import es.javiercarrasco.ejemplologin.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailCoffeeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    private val remoteDatasource: RemoteDataSource
    private val localDatasource: LocalDatasource
    private val sessionManager: SessionManager

    data class CoffeeUiState(
        val comments: List<Comment> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val dialogComment: Boolean = false
    )

    // Estado del login.
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)

    private val _stateCoffeeDetail = MutableStateFlow<Coffee?>(null)
    val stateCoffeeDetail: StateFlow<Coffee?> = _stateCoffeeDetail.asStateFlow()

    private val _stateComments = MutableStateFlow<List<Comment>>(emptyList())
    val stateComments: StateFlow<List<Comment>> = _stateComments.asStateFlow()

    private val _coffeeUiState = MutableStateFlow(CoffeeUiState())
    val coffeeUiState: StateFlow<CoffeeUiState> = _coffeeUiState.asStateFlow()

    init {
        val db = AppDatabase.getInstance(application)
        localDatasource = LocalDatasource(db.coffeesDao())
        remoteDatasource = RemoteDataSource()
        repository = Repository(localDatasource)

        val dataStore: DataStore<Preferences> = application.dataStore
        sessionManager = SessionManager(dataStore)
    }

    fun logout() {
        viewModelScope.launch {
            _loginState.value = LoginState.Idle
            sessionManager.clearSession()
        }
    }

    fun getCoffeeDetail(coffeeId: Int) {
        viewModelScope.launch {
            sessionManager.sessionFlow.collect {
                try {
                    it.first?.let {
                        _stateCoffeeDetail.value = repository.fetchCoffeeDetail(it, coffeeId)
                        // Aquí puedes manejar la lista de café obtenida.
                        Log.d(
                            "DetailCoffeeViewModel",
                            "Café obtenido: ${_stateCoffeeDetail.value!!.id}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("DetailCoffeeViewModel", "Error al obtener el café", e)
                }
            }
        }
    }

    fun getCommentsByCoffeeId(coffeeId: Int) {
        viewModelScope.launch {
            sessionManager.sessionFlow.collect {
                try {
                    it.first?.let {
                        _stateComments.value = (repository.fetchCommentsByCoffeeId(it, coffeeId)).sortedByDescending{
                                it.id
                        }
                        // Aquí puedes manejar la lista de comentarios obtenida.
                        Log.d(
                            "DetailCoffeeViewModel",
                            "Comentarios obtenidos: ${_stateComments.value.size}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e("DetailCoffeeViewModel", "Error al obtener los comentarios", e)
                }
            }
        }
    }

    fun showDialogComment(show: Boolean) {
        _coffeeUiState.value = _coffeeUiState.value.copy(
            dialogComment = show
        )
    }

    fun putComment(idCoffee: Int, comment: String) {
        viewModelScope.launch {
            sessionManager.sessionFlow.collect {
                if (it.first != null) {
                    val comment = Comment(
                        idCoffee = idCoffee,
                        user = it.second!!,
                        comment = comment
                    )
                    if (repository.putComment(it.first!!, comment) != null)
                        getCommentsByCoffeeId(idCoffee)
                }
            }
        }
    }
}