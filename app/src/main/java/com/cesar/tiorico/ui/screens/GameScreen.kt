package com.cesar.tiorico.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesar.tiorico.viewmodel.GameViewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    onExit: () -> Unit = {}
) {

    val state = viewModel.state
    val jugador = state.jugadores.getOrNull(state.jugadorActualIndex)

    val dinero = jugador?.dinero ?: 0

    // 🔥 Animaciones
    val dineroAnimado by animateIntAsState(targetValue = dinero)
    val progresoAnimado by animateFloatAsState(
        targetValue = (dinero.toFloat() / state.meta).coerceIn(0f, 1f)
    )

    // 🎨 Color dinámico
    val colorDinero = when {
        dinero <= 0 -> Color.Red
        dinero < state.meta / 2 -> Color(0xFFFFC107)
        else -> Color(0xFF4CAF50)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔝 HEADER
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    "👤 Turno de: ${jugador?.nombre ?: "-"}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text("💰 Dinero", color = Color.White)

                Text(
                    "$${dineroAnimado}",
                    color = colorDinero,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text("🔄 Turno global: ${state.turno}", color = Color.White)
                Text("🎯 Meta: $${state.meta}", color = Color.White)

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = progresoAnimado,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🎯 ACCIÓN ACTUAL
        if (state.accionActual.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "🎯 ${state.accionActual}",
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }

        // 📊 MENSAJE
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = state.mensaje,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🎮 BOTONES (solo si el juego sigue)
        if (!state.juegoTerminado) {
            ActionButton("🟢 Ahorrar", Color(0xFF4CAF50)) { viewModel.ahorrar() }
            ActionButton("🟡 Invertir", Color(0xFFFFC107)) { viewModel.invertir() }
            ActionButton("🔴 Gastar", Color(0xFFF44336)) { viewModel.gastar() }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🚪 SALIR
        Button(
            onClick = onExit,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("🚪 Salir")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 👥 JUGADORES (TABLERO)
        Text(
            "👥 Jugadores",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(state.jugadores) { p ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (p == jugador) Color(0xFFE3F2FD) else Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(p.nombre)
                        Text("💰 $${p.dinero}")
                    }
                }
            }
        }

        // 🏆 RESULTADO FINAL
        if (state.juegoTerminado) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🏆 Ganador: ${state.ganador}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text, color = Color.White)
    }
}