package edu.victoraracil.apirestwords.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("words")
data class Word(
    @PrimaryKey(autoGenerate = true) @SerializedName("idWord") var idWord: Int = 0,
    @SerializedName("word") var word: String = "",
    @SerializedName("definition") var definition: String = "",
    @Ignore var favorite: Boolean = false
)