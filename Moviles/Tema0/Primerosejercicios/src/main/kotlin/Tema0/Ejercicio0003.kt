package com.dam.Tema0

fun main(){
    println("Introduce un dia de la semana por numero")
    val num = readln().toInt()

    when(num){
        1 -> println("Lunes")
        2 -> println("Martes")
        3 -> println("Miercoles")
        4 -> println("Jueves")
        5 -> println("Viernes")
        6,7 -> println("Fin de semana")
        else -> println("Numero no valido")
    }
}