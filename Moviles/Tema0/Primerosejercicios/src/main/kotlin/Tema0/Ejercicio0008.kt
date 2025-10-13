package com.dam.Tema0

import java.util.Locale
import java.util.Locale.getDefault

fun main(){
    val palabras = mutableListOf<String>()
    var x = true
    while (x == true){
        println("Escribe que palabra quieres añadir a la lista (escribe \"fin\" para terminar)")
        val palabra = readln().uppercase(getDefault())
        if (palabra == "FIN"){x = false}
        else {palabras.add(palabra)}
    }

    for(i in palabras.size - 1 downTo 0)print("${palabras[i]} ")
    println()
    palabras.forEach { print("$it ") }
}