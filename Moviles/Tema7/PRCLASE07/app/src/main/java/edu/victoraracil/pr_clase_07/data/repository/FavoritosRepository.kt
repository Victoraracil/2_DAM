package edu.victoraracil.pr_clase_07.data.repository

import edu.victoraracil.pr_clase_07.data.local.EditorialFavoritaDao
import edu.victoraracil.pr_clase_07.data.local.EditorialFavoritaEntity
import kotlinx.coroutines.flow.Flow

class FavoritosRepository(
    private val dao: EditorialFavoritaDao
) {
    fun getAllFavoritas(): Flow<List<EditorialFavoritaEntity>> = dao.getAllFavoritas()

    fun esFavorita(id: Int): Flow<Boolean> = dao.esFavorita(id)

    suspend fun insertar(editorial: EditorialFavoritaEntity) = dao.insertar(editorial)

    suspend fun eliminarPorId(id: Int) = dao.eliminarPorId(id)
}
