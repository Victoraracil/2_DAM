package edu.practico.seriesapp.data

import edu.practico.seriesapp.data.model.Character
import edu.practico.seriesapp.data.model.Show
import edu.practico.seriesapp.data.remote.RemoteDataSource
import retrofit2.Response

class Repository(
    private val remoteDataSource: RemoteDataSource,
    //private val localDataSource: LocalDataSource
) {

    fun getShowDetailById(id: Int): Show {
        val remote = remoteDataSource.getShowDetailById(id)
        return remote
    }

     suspend fun getCharacters(): Response<List<Character>> {
        return remoteDataSource.getCharacters()
    }
}