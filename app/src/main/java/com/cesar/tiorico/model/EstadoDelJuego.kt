package com.cesar.tiorico.model
data class GameState(
    val dinero: Int = 1000,
    val turno: Int = 1,
    val meta: Int = 5000,
    val mensaje: String = "¡Comienza el juego!",
    val juegoTerminado: Boolean = false,
    val gano: Boolean = false,
    val historial : List < String > = emptyList ()
)