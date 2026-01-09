package edu.victoraracil.tema7test.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Post.kt
@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)