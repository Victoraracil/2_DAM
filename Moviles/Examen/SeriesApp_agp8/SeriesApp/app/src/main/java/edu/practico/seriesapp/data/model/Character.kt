package edu.practico.seriesapp.data.model

import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

@Entity("charactersFavs")
data class Character(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("imageUrl")
    var imageUrl: String = "",
    @SerializedName("idShow")
    var idShow: Int?,
    @SerializedName("idActor")
    val idActor: Int?,
    @Ignore var favorite: Boolean = false
)