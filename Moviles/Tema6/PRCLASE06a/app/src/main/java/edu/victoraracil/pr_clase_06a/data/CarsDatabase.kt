package edu.victoraracil.pr_clase_06a.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.victoraracil.pr_clase_06a.data.model.Brand
import edu.victoraracil.pr_clase_06a.data.model.Car

//Base de datos
@Database(
    entities = [Brand::class, Car::class], version = 1, exportSchema = false
)
abstract class CarsDatabase : RoomDatabase() {


    // Método abstracto para acceder al DAO
    abstract fun carsDao(): CarsDao

    companion object {
        @Volatile
        private var INSTANCE: CarsDatabase? = null

        fun getDatabase(context: Context): CarsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, CarsDatabase::class.java, "cars_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
