package com.dam.Tema0.Ejercicio0009

import sun.util.calendar.BaseCalendar

class Coche (var marca: String, var modelo: String, var anyo: String){

    override fun toString(): String {
        return "$marca $modelo $anyo"
    }
}