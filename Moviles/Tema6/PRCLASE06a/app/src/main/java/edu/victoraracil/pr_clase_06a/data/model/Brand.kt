package edu.victoraracil.pr_clase_06a.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brands")
data class Brand(
    @PrimaryKey(autoGenerate = true) val idBrand: Int = 0, val name: String
)
