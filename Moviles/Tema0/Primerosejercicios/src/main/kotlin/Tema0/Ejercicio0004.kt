package com.dam.Tema0

fun main(){
    println("Introduce un numero para mostrar su tabla de multiplicar")
    val num = readln().toInt()

    for (i in 1..10){
        println("$num X $i = ${num * i}")
    }
}