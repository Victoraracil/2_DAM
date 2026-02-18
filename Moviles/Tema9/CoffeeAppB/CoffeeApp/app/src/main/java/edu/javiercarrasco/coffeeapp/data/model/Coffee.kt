package es.javiercarrasco.ejemplologin.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "coffees")
data class Coffee(
    @SerializedName("id")
    @PrimaryKey
    var id: Int? = null,
    @SerializedName("coffee_name")
    var coffeeName: String? = null,
    @SerializedName("coffee_desc")
    var coffeeDesc: String? = null,
    @SerializedName("comments")
    var comments: String? = null
)