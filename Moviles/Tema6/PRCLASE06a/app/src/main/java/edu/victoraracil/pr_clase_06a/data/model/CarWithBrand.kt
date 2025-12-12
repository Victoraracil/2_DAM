package edu.victoraracil.pr_clase_06a.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CarWithBrand(
    @Embedded val car: Car, @Relation(
        parentColumn = "idBrand", entityColumn = "idBrand"
    ) val brand: Brand
)