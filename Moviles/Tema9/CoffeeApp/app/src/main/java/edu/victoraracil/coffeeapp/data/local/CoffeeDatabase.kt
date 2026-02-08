package edu.victoraracil.coffeeapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.victoraracil.coffeeapp.domain.model.Coffee
import edu.victoraracil.coffeeapp.domain.model.Comment

@Database(
    entities = [Coffee::class, Comment::class], version = 1
)
abstract class CoffeeDatabase : RoomDatabase() {

    abstract fun coffeeDao(): CoffeeDao

    companion object {
        @Volatile
        private var INSTANCE: CoffeeDatabase? = null

        fun getDatabase(context: Context): CoffeeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, CoffeeDatabase::class.java, "coffee_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
