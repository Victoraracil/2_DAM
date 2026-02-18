package edu.practico.seriesapp.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.practico.seriesapp.data.Repository
import edu.practico.seriesapp.data.model.Character
import edu.practico.seriesapp.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class CharacterViewModel : ViewModel() {

    private val remoteDatasource = RemoteDataSource()
    private val repository = Repository(remoteDatasource)

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getCharacters() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                val response: Response<List<Character>> =
                    repository.getCharacters()

                Log.d("CharacterVM", "Código HTTP: ${response.code()}")

                if (response.isSuccessful) {
                    val body = response.body()

                    Log.d("CharacterVM", "Body: $body")

                    _characters.value = body ?: emptyList()
                } else {
                    _error.value = "Error ${response.code()}: ${response.message()}"
                    Log.e("CharacterVM", "Error HTTP: ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                Log.e("CharacterVM", "Excepción", e)
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _loading.value = false
            }
        }
    }
}

