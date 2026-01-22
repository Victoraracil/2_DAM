package edu.victoraracil.pr_clase_07.data.model


import com.google.gson.annotations.SerializedName

data class Editorial(
    @SerializedName("editorial")
    val editorial: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("url")
    val url: String
)