package edu.victoraracil.pr_clase_07.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "editoriales_favoritas")
data class EditorialFavoritaEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val anioFundacion: Int,
    val url: String,
    val logoUrl: String
)
