package edu.javiercarrasco.coffeeapp.ui.screens

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.javiercarrasco.coffeeapp.data.local.AppDatabase
import edu.javiercarrasco.coffeeapp.data.local.LocalDatasource
import es.javiercarrasco.ejemplologin.data.Repository
import es.javiercarrasco.ejemplologin.data.model.Coffee
import es.javiercarrasco.ejemplologin.data.model.LoginState
import es.javiercarrasco.ejemplologin.data.model.SessionManager
import es.javiercarrasco.ejemplologin.data.model.dataStore
import es.javiercarrasco.ejemplologin.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CoffeeListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    private val remoteDatasource: RemoteDataSource
    private val localDatasource: LocalDatasource
    private val sessionManager: SessionManager

    data class CoffeeListUiState(
        val coffeeList: List<Coffee> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val loginState: LoginState = LoginState.Idle
    )

    private val _stateCoffeeList = MutableStateFlow<List<Coffee>>(emptyList())

    // Estado de la UI.
    val uiState: StateFlow<CoffeeListUiState>
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)

    private val _quizCoffee = MutableStateFlow<Coffee?>(null)
    val quizCoffee: StateFlow<Coffee?> = _quizCoffee

    private val _quizOptions = MutableStateFlow<List<String>>(emptyList())
    val quizOptions: StateFlow<List<String>> = _quizOptions

    private val _showQuizDialog = MutableStateFlow(false)
    val showQuizDialog: StateFlow<Boolean> = _showQuizDialog

    val quizEnabled = _stateCoffeeList.map { list ->
        list.count { it.coffeeDesc != null } >= 3
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    init {
        val db = AppDatabase.getInstance(application)
        localDatasource = LocalDatasource(db.coffeesDao())
        remoteDatasource = RemoteDataSource()
        repository = Repository(localDatasource)

        val dataStore: DataStore<Preferences> = application.dataStore
        sessionManager = SessionManager(dataStore)




        uiState = combine(
            _stateCoffeeList, _isLoading, _errorMessage, _loginState
        ) { coffeeList, isLoading, errorMessage, loginState ->
            CoffeeListUiState(
                coffeeList = coffeeList,
                isLoading = isLoading,
                errorMessage = errorMessage,
                loginState = loginState
            )
            // Este segundo combine es necesario para añadir un sexto flow.
        }.stateIn(
            viewModelScope, SharingStarted.Eagerly, CoffeeListUiState()
        )

        getCoffees()
    }

    fun logout() {
        viewModelScope.launch {
            _loginState.value = LoginState.Idle
            sessionManager.clearSession()
        }
    }

    fun getCoffees() {
        viewModelScope.launch {

            sessionManager.sessionFlow.collect { session ->

                val token = session.first ?: return@collect

                //Cargar y guardar listado
                repository.fetchCoffees(token)

                //Observar Room
                launch {
                    repository.localDataSource.getAllCoffees().collect { list ->
                        _stateCoffeeList.value = list
                    }
                }

                //Lanzar precarga en segundo plano
                launch {
                    repository.preloadDescriptions(token)
                }
            }
        }
    }


    fun generateQuiz() {

        val coffeesWithDesc = _stateCoffeeList.value.filter { !it.coffeeDesc.isNullOrBlank() }

        if (coffeesWithDesc.size < 3) return

        val selected = coffeesWithDesc.shuffled().take(3)

        val correctCoffee = selected.first()

        val options = selected.mapNotNull { it.coffeeDesc }.shuffled()

        _quizCoffee.value = correctCoffee
        _quizOptions.value = options
        _showQuizDialog.value = true
    }

    fun closeQuiz() {
        _showQuizDialog.value = false
    }

    fun checkQuizAnswer(selectedDesc: String): Boolean {

        val correct = _quizCoffee.value?.coffeeDesc

        _showQuizDialog.value = false

        return selectedDesc == correct
    }


}