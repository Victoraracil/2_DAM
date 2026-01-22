package edu.victoraracil.pr_clase_07.data.repository

import edu.victoraracil.pr_clase_07.data.model.Comic
import edu.victoraracil.pr_clase_07.data.model.Editorial
import edu.victoraracil.pr_clase_07.data.remote.RetrofitClient
import edu.victoraracil.pr_clase_07.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EditorialRepository {

    fun getEditoriales(): Flow<Resource<List<Editorial>>> = flow {
        emit(Resource.Loading)

        try {
            val resultado = RetrofitClient.api.getEditoriales()
            emit(Resource.Success(resultado))
        } catch (e: Exception) {
            emit(Resource.Error("Error al cargar editoriales: ${e.message}"))
        }
    }

    fun getComicsByEditorial(editorialId: Int): Flow<Resource<List<Comic>>> = flow {
        emit(Resource.Loading)

        try {
            val resultado = RetrofitClient.api.getComicsByEditorial(editorialId)
            emit(Resource.Success(resultado))
        } catch (e: Exception) {
            emit(Resource.Error("Error al cargar cómics: ${e.message}"))
        }
    }
}
