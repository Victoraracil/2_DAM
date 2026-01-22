package edu.victoraracil.pr_clase_07.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "editoriales_db"
            ).build()

            INSTANCE = db
            db
        }
    }
}
