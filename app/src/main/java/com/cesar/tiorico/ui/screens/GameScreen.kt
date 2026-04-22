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
fun GameScreen(viewModel: GameViewModel = viewModel()) {

    val state = viewModel.state

    // 🔥 Animaciones
    val dineroAnimado by animateIntAsState(targetValue = state.dinero)
    val progresoAnimado by animateFloatAsState(
        targetValue = (state.dinero.toFloat() / state.meta).coerceIn(0f, 1f)
    )

    // 🎨 Color dinámico
    val colorDinero = when {
        state.dinero <= 0 -> Color.Red
        state.dinero < state.meta / 2 -> Color(0xFFFFC107)
        else -> Color(0xFF4CAF50)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔝 HEADER + PROGRESO
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    "💰 Dinero: $${dineroAnimado}",
                    color = colorDinero,
                    fontWeight = FontWeight.Bold
                )

                Text("🔄 Turno: ${state.turno}", color = Color.White)
                Text("🎯 Meta: $${state.meta}", color = Color.White)

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = progresoAnimado,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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

        // 🎮 BOTONES
        ActionButton("🟢 Ahorrar", Color(0xFF4CAF50)) { viewModel.ahorrar() }
        ActionButton("🟡 Invertir", Color(0xFFFFC107)) { viewModel.invertir() }
        ActionButton("🔴 Gastar", Color(0xFFF44336)) { viewModel.gastar() }

        Spacer(modifier = Modifier.height(16.dp))

        // 🚪 SALIR
        Button(
            onClick = { viewModel.reiniciarJuego() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("🚪 Salir")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📜 HISTORIAL
        Text("📜 Historial", fontWeight = FontWeight.Bold)

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(state.historial.takeLast(5).reversed()) { item ->
                Text("• $item")
            }
        }

        // 🏆 RESULTADO (solo visual, ya navega aparte)
        if (state.juegoTerminado) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (state.gano) Color(0xFF4CAF50) else Color(0xFFF44336)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (state.gano) "🏆 ¡GANASTE!" else "💀 PERDISTE",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
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