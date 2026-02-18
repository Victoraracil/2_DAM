package edu.practico.seriesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "actors")
data class Actor(
    @SerializedName("id")
    @PrimaryKey
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("surname")
    var surname: String,
    @SerializedName("imageUrl")
    var imageUrl: String
)