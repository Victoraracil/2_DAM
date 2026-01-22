package edu.victoraracil.pr_clase_07.data.model


import com.google.gson.annotations.SerializedName

data class Comic(
    @SerializedName("author")
    val author: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("editorial")
    val editorial: Editorial,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)