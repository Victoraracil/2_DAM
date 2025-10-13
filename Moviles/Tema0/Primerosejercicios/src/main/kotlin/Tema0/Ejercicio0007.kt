package com.dam.Tema0
fun main(){
    val semana = arrayOf("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo")
    println("Introduce un numero para mostrar un dia de la semana (Del 1 al 7)")
    val num = readln().toInt() - 1

    if (num in 0..7){println("Hoy es ${semana[num]}")}
    else {
        println("Fecha incorrecta")
        for ((position, valor) in semana.withIndex())
            println("Dia ${position + 1}  $valor")
    }
}
