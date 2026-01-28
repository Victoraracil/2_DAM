package edu.victoraracil.apirestwords.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.victoraracil.apirestwords.data.model.Word

@Database(
    entities = [Word::class], version = 1, exportSchema = true // Importante para migraciones
)
abstract class WordsDatabase : RoomDatabase() {

    // Conexión con DAO de palabras
    abstract fun wordDao(): WordsDAO

    companion object {

        @Volatile
        private var INSTANCE: WordsDatabase? = null

        fun getInstance(context: Context): WordsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, WordsDatabase::class.java, "Words.db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
