package com.dam.Tema0.Ejercicio0010

class Libro (autor: String, titulo: String, anyo: String) {

    /*var autor: String = autor.ifEmpty { "Anonimo" }
    var titulo: String = titulo.ifEmpty { "No indicado" }
    var anyo: String = anyo.ifEmpty { "-1" }*/

    //No llegaba a asignar los valores correctamente
    var autor: String = autor
        set(value) { field = if (value.isEmpty()) "Anonimo" else value }
        get() = field

    var titulo: String = titulo
        set(value) { field = if (value.isEmpty()) "No indicado" else value }
        get() = field

    var anyo: String = anyo
        set(value) { field = if (value.isEmpty()) "-1" else value }
        get() = field

    init {
        this.autor = autor
        this. titulo = titulo
        this.anyo = anyo
    }

    override fun toString(): String {
        return "$autor - $titulo - $anyo"
    }
}