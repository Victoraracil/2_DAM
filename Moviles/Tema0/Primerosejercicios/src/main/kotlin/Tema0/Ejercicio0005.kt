package com.dam.Tema0

import kotlin.text.iterator

fun main(){
    val texto : String = readln()

    for(i in texto)print("$i ")
    println()
    for(i in texto.length - 1 downTo 0)print("${texto[i]} ")
}