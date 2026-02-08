package edu.victoraracil.coffeeapp.domain.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "comments")

data class Comment(
    @PrimaryKey(autoGenerate = true) @SerializedName("id") val id: Int,
    @SerializedName("comment") val comment: String,
    @SerializedName("idCoffee") val idCoffee: Int,
    @SerializedName("user") val author: String
)