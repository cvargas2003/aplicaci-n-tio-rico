package com.cesar.tiorico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cesar.tiorico.viewmodel.GameViewModel

@Composable
fun ResultScreen(
    viewModel: GameViewModel,
    onRestart: () -> Unit,
    onExit: () -> Unit
) {

    val state = viewModel.state

    // 🏆 ranking (ordenados por dinero)
    val ranking = state.jugadores.sortedByDescending { it.dinero }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // 🏆 GANADOR
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "🏆 Ganador",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = state.ganador,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("🎯 Meta: $${state.meta}", color = Color.White)
                Text("🔄 Turnos: ${state.turno}", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 👥 RANKING
        Text(
            "🏅 Ranking final",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(ranking) { jugador ->

                val esGanador = jugador.nombre == state.ganador

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (esGanador) Color(0xFFE8F5E9) else Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            jugador.nombre,
                            fontWeight = if (esGanador) FontWeight.Bold else FontWeight.Normal
                        )
                        Text("💰 $${jugador.dinero}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔄 REINICIAR
        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔄 Jugar de nuevo")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 🚪 SALIR
        Button(
            onClick = onExit,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("🚪 Salir")
        }
    }
}