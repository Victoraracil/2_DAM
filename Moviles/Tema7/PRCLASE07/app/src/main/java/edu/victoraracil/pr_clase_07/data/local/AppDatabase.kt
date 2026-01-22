package edu.victoraracil.pr_clase_07.data.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EditorialFavoritaEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun editorialFavoritaDao(): EditorialFavoritaDao
}
