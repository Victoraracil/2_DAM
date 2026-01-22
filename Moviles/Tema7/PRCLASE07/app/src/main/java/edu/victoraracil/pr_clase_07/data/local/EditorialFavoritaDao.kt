package edu.victoraracil.pr_clase_07.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EditorialFavoritaDao {

    @Query("SELECT * FROM editoriales_favoritas")
    fun getAllFavoritas(): Flow<List<EditorialFavoritaEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM editoriales_favoritas WHERE id = :id)")
    fun esFavorita(id: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(editorial: EditorialFavoritaEntity)

    @Query("DELETE FROM editoriales_favoritas WHERE id = :id")
    suspend fun eliminarPorId(id: Int)
}
