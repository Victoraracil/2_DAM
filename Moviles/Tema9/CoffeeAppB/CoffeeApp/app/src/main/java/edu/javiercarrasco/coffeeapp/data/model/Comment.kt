package edu.javiercarrasco.coffeeapp.data.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("idCoffee")
    var idCoffee: Int?,
    @SerializedName("user")
    var user: String?,
    @SerializedName("comment")
    var comment: String?
)
