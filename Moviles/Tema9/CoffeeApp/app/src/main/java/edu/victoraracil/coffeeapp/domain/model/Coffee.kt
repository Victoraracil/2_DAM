package edu.victoraracil.coffeeapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coffees")

data class Coffee(
    @PrimaryKey @SerializedName("id") var id: Int?,
    @SerializedName("coffee_name") var coffeeName: String?,
    @SerializedName("coffee_desc") var coffeeDesc: String?,
    @SerializedName("comments") var comments: String?
)