package edu.victoraracil.pr_clase_06a.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cars", foreignKeys = [ForeignKey(
        entity = Brand::class,
        parentColumns = ["idBrand"],
        childColumns = ["idBrand"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index(value = ["idBrand"])]
)
data class Car(
    @PrimaryKey(autoGenerate = true) val idCar: Int = 0,
    val model: String,
    val motor: String,
    val year: Int,
    val favorite: Boolean = false,
    val idBrand: Int = 0
)
