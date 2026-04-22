package com.cesar.tiorico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesar.tiorico.viewmodel.GameViewModel

@Composable
fun PlayerSetupScreen(
    viewModel: GameViewModel = viewModel(),
    onStart: () -> Unit
) {
    var cantidad by remember { mutableStateOf(2f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "👥 Número de jugadores: ${cantidad.toInt()}",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        Slider(
            value = cantidad,
            onValueChange = { cantidad = it },
            valueRange = 2f..5f,
            steps = 2
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.iniciarJuego(cantidad.toInt(), 5000)
                onStart()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar partida")
        }
    }
}