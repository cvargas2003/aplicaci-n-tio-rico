package com.cesar.tiorico.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.cesar.tiorico.model.GameState
import com.cesar.tiorico.model.Player
import kotlin.random.Random

class GameViewModel : ViewModel() {

    var state by mutableStateOf(GameState())
        private set

    // 🔥 INICIAR JUEGO
    fun iniciarJuego(cantidad: Int, meta: Int) {
        val jugadores = List(cantidad) {
            Player(nombre = "Jugador ${it + 1}")
        }

        state = GameState(
            jugadores = jugadores,
            meta = meta
        )
    }

    // 🔁 REINICIAR
    fun reiniciarJuego() {
        state = GameState(meta = state.meta)
    }

    private fun jugadorActual(): Player? {
        return state.jugadores.getOrNull(state.jugadorActualIndex)
    }

    // 🟢
    fun ahorrar() = ejecutarAccion(200, "Ahorró +200")

    // 🟡
    fun invertir() {
        val valor = if (Random.nextBoolean()) 500 else -300
        val msg = if (valor > 0) "Ganó +$valor" else "Perdió $valor"
        ejecutarAccion(valor, "Invirtió: $msg")
    }

    // 🔴
    fun gastar() = ejecutarAccion(-150, "Gastó -150")

    // 🎯 LÓGICA CENTRAL
    private fun ejecutarAccion(valor: Int, accion: String) {

        if (state.juegoTerminado) return

        val jugador = jugadorActual() ?: return

        var dineroNuevo = jugador.dinero + valor
        var mensaje = "${jugador.nombre}: $accion"

        // 🎲 EVENTO
        if (Random.nextFloat() < 0.6f) {
            val (v, m) = evento()
            dineroNuevo += v
            mensaje += "\n$m"
        }

        // 🏆 GANA POR META
        if (dineroNuevo >= state.meta) {
            actualizarJugador(dineroNuevo)
            state = state.copy(
                mensaje = "$mensaje\n🏆 ${jugador.nombre} alcanzó la meta",
                juegoTerminado = true,
                ganador = jugador.nombre,
                historial = state.historial + mensaje
            )
            return
        }

        // 💀 ELIMINADO
        if (dineroNuevo <= 0) {

            val lista = state.jugadores.toMutableList()
            lista.removeAt(state.jugadorActualIndex)

            // 🔥 SI SOLO QUEDA 1 → GANA AUTOMÁTICO
            if (lista.size == 1) {
                state = state.copy(
                    jugadores = lista,
                    mensaje = "$mensaje\n💀 ${jugador.nombre} eliminado",
                    juegoTerminado = true,
                    ganador = lista.first().nombre,
                    historial = state.historial + mensaje
                )
                return
            }

            // 🔥 SI NO QUEDAN JUGADORES (caso extremo)
            if (lista.isEmpty()) {
                state = state.copy(
                    jugadores = emptyList(),
                    mensaje = "Todos los jugadores fueron eliminados",
                    juegoTerminado = true,
                    historial = state.historial + mensaje
                )
                return
            }

            val nuevoIndex =
                if (state.jugadorActualIndex >= lista.size) 0 else state.jugadorActualIndex

            state = state.copy(
                jugadores = lista,
                jugadorActualIndex = nuevoIndex,
                turno = state.turno + 1,
                mensaje = "$mensaje\n💀 ${jugador.nombre} eliminado",
                historial = state.historial + mensaje
            )
            return
        }

        // 🔄 TURNO NORMAL
        actualizarJugador(dineroNuevo)

        val siguiente = (state.jugadorActualIndex + 1) % state.jugadores.size

        state = state.copy(
            jugadorActualIndex = siguiente,
            turno = state.turno + 1,
            mensaje = mensaje,
            accionActual = accion,
            historial = state.historial + mensaje
        )
    }

    // 🔧 ACTUALIZAR JUGADOR
    private fun actualizarJugador(nuevoDinero: Int) {
        val lista = state.jugadores.toMutableList()
        val jugador = lista[state.jugadorActualIndex]
        lista[state.jugadorActualIndex] = jugador.copy(dinero = nuevoDinero)
        state = state.copy(jugadores = lista)
    }

    // 🎲 EVENTOS
    private fun evento(): Pair<Int, String> {
        return when (Random.nextInt(1, 5)) {
            1 -> 300 to "🎉 Lotería +300"
            2 -> -200 to "⚠ Multa -200"
            3 -> 150 to "💰 Bono +150"
            else -> -100 to "💸 Gasto inesperado -100"
        }
    }
}