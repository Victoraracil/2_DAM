package edu.victoraracil.pr_clase_07.data.remote

import edu.victoraracil.pr_clase_07.data.model.Comic
import edu.victoraracil.pr_clase_07.data.model.Editorial
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Lista de editoriales
    @GET("editorials")
    suspend fun getEditoriales(): List<Editorial>

    // Lista de comics por editorial
    @GET("editorials/{id}/comics")
    suspend fun getComicsByEditorial(
        @Path("id") editorialId: Int
    ): List<Comic>
}
