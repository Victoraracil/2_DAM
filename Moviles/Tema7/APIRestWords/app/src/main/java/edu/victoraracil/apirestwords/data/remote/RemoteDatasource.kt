package edu.victoraracil.apirestwords.data.remote

class RemoteDatasource {

    suspend fun getAllWords() = RetrofitProvider.apiService.getAllWords()
}
