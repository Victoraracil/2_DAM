package edu.victoraracil.notespmdm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(

    @PrimaryKey(autoGenerate = true) val idNote: Long = 0,

    val title: String = "",

    val description: String = "",

    val date: String = ""
)
