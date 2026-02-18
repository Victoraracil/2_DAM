package edu.practico.seriesapp.data.model

import com.google.gson.annotations.SerializedName

data class Show(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("title")
    var title: String,
    @SerializedName("synopsis")
    var synopsis: String,
    @SerializedName("coverImageUrl")
    var coverImageUrl: String,
    @SerializedName("characters")
    val characters: List<Character> = emptyList()
)