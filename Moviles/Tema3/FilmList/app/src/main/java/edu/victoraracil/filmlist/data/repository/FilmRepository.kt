package edu.victoraracil.filmlist.data.repository

import android.content.Context
import edu.victoraracil.filmlist.R
import edu.victoraracil.filmlist.data.model.Film
import java.io.BufferedReader
import java.io.InputStreamReader

class FilmRepository(private val context: Context) {

    fun readRawFile(): MutableList<Film> {
        val films = mutableListOf<Film>()

        try {
            // Abre el archivo films.csv desde res/raw
            val inputStream = context.resources.openRawResource(R.raw.films)
            val reader = BufferedReader(InputStreamReader(inputStream))

            reader.forEachLine { line ->

                // Saltamos líneas vacías
                if (line.isNotBlank()) {

                    // Separar campos por ;
                    val parts = line.split(";")

                    // Comprobamos que tenga los 7 campos esperados
                    if (parts.size == 7) {

                        val film = Film(
                            id = parts[0].toInt(),
                            title = parts[1],
                            year = parts[2].toInt(),
                            duration = parts[3].toInt(),
                            genre = parts[4],
                            director = parts[5],
                            cover = parts[6]
                        )

                        films.add(film)
                    }
                }
            }

            reader.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return films
    }
}
