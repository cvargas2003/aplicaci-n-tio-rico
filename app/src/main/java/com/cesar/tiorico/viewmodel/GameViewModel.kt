package com.cesar.tiorico.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.cesar.tiorico.model.GameState
import kotlin.random.Random

class GameViewModel : ViewModel() {

    var state by mutableStateOf(GameState())
        private set

    // 🎯 Seleccionar meta
    fun seleccionarMeta(metaSeleccionada: Int) {
        state = state.copy(meta = metaSeleccionada)
    }

    // 🔄 Reiniciar juego
    fun reiniciarJuego() {
        state = GameState(meta = state.meta)
    }

    // 🟢 AHORRAR
    fun ahorrar() {
        ejecutarAccion(200, "🟢 Ahorraste +200")
    }

    // 🟡 INVERTIR
    fun invertir() {
        val resultado = if (Random.nextBoolean()) 500 else -300
        val mensaje = if (resultado > 0)
            "🟡 Invertiste y ganaste +$resultado"
        else
            "🟡 Invertiste y perdiste $resultado"

        ejecutarAccion(resultado, mensaje)
    }

    // 🔴 GASTAR
    fun gastar() {
        ejecutarAccion(-150, "🔴 Gastaste -150")
    }

    // 🎯 FUNCIÓN CENTRAL (MEJORA GRANDE)
    private fun ejecutarAccion(valor: Int, mensajeAccion: String) {

        if (state.juegoTerminado) return

        var dineroNuevo = state.dinero + valor
        var mensajeFinal = mensajeAccion

        // 🎲 EVENTO SOLO EN ALGUNOS TURNOS (60%)
        if (Random.nextFloat() < 0.6f) {
            val (valorEvento, mensajeEvento) = generarEvento()
            dineroNuevo += valorEvento
            mensajeFinal += "\n$mensajeEvento"
        }

        val resultado = verificarEstado(dineroNuevo, mensajeFinal)

        state = state.copy(
            dinero = resultado.dinero,
            turno = state.turno + 1,
            mensaje = resultado.mensaje,
            juegoTerminado = resultado.terminado,
            gano = resultado.gano,
            historial = state.historial + resultado.mensaje
        )
    }

    // 🎲 EVENTOS
    private fun generarEvento(): Pair<Int, String> {
        return when (Random.nextInt(1, 6)) {
            1 -> Pair(300, "🎉 Evento: Ganaste la lotería +300")
            2 -> Pair(-200, "⚠ Evento: Pagaste multa -200")
            3 -> Pair(150, "💰 Evento: Bono inesperado +150")
            4 -> Pair(-120, "💸 Evento: Gasto médico -120")
            5 -> Pair(250, "📈 Evento: Inversión extra +250")
            else -> Pair(-80, "📉 Evento: Pérdida menor -80")
        }
    }

    // 🏆 💀 VALIDACIÓN CENTRALIZADA
    private fun verificarEstado(dinero: Int, mensajeBase: String): Resultado {

        var mensajeFinal = mensajeBase
        var terminado = false
        var gano = false

        when {
            dinero >= state.meta -> {
                mensajeFinal += "\n🏆 ¡Meta alcanzada!"
                terminado = true
                gano = true
            }

            dinero <= 0 -> {
                mensajeFinal += "\n💀 Te quedaste sin dinero"
                terminado = true
                gano = false
            }
        }

        return Resultado(dinero, mensajeFinal, terminado, gano)
    }

    // 📦 DATA INTERNA
    data class Resultado(
        val dinero: Int,
        val mensaje: String,
        val terminado: Boolean,
        val gano: Boolean
    )
}