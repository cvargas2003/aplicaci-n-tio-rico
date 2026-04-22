package com.cesar.tiorico.model

data class GameState(
    val jugadores: List<Player> = emptyList(),
    val jugadorActualIndex: Int = 0,
    val turno: Int = 1,
    val meta: Int = 5000,
    val mensaje: String = "¡Comienza el juego!",
    val accionActual: String = "",
    val juegoTerminado: Boolean = false,
    val ganador: String = "",
    val historial: List<String> = emptyList()
)