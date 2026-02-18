package edu.javiercarrasco.coffeeapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.javiercarrasco.ejemplologin.data.model.Coffee

// AppDatabase.kt
@Database(
    entities = [Coffee::class],
    version = 1,
    exportSchema = false // Importante para migraciones
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coffeesDao(): CoffeesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "coffees.db"
                ).fallbackToDestructiveMigration(true)
                    .build()

                INSTANCE = instance // Asigna la instancia a la variable volátil.
                instance // Devuelve la instancia de la base de datos.
            }
        }
    }
}