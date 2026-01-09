package edu.victoraracil.notespmdm.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.victoraracil.notespmdm.data.datasource.NotesDao
import edu.victoraracil.notespmdm.data.model.Note

@Database(
    entities = [Note::class], version = 1, exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, NotesDatabase::class.java, "notes.db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}