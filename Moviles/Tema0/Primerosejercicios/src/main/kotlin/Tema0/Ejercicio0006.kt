package com.dam.Tema0

fun main(){
    println("Introduce un numero para saber si es primo")
    val num = readln().toInt()
    println(if (esprimo(n1 = num)) "El numero $num es primo" else "El numero $num no es primo")
}

fun esprimo(n1: Int): Boolean{
    var contador = 0
    for(i in 1..n1){
        if (n1 % i == 0){
            contador++
        }
    }
    return contador <= 2
}