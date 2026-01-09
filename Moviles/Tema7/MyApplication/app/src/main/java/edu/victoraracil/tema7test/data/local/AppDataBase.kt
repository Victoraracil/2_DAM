package edu.victoraracil.tema7test.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.victoraracil.tema7test.data.model.Post

// AppDatabase.kt
@Database(
    entities = [Post::class],
    version = 1,
    exportSchema = true // Importante para migraciones
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postsDAO(): PostsDAO // Conexión con DAO de Posts.

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Posts.db"
                ).fallbackToDestructiveMigration(true) // Solo en desarrollo.
                    .build()

                INSTANCE = instance // Asigna la instancia a la variable volátil.
                instance // Devuelve la instancia de la base de datos.
            }
        }
    }
}