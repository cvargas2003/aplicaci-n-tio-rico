package com.cesar.tiorico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesar.tiorico.viewmodel.GameViewModel

@Composable
fun MetaScreen(
    viewModel: GameViewModel = viewModel(),
    onStartGame: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("🎯 Elige tu meta", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        MetaButton("$3,000 (Fácil)", Color(0xFF4CAF50)) {
            viewModel.seleccionarMeta(3000)
            onStartGame()
        }

        MetaButton("$5,000 (Normal)", Color(0xFFFFC107)) {
            viewModel.seleccionarMeta(5000)
            onStartGame()
        }

        MetaButton("$10,000 (Difícil)", Color(0xFFF44336)) {
            viewModel.seleccionarMeta(10000)
            onStartGame()
        }
    }
}

@Composable
fun MetaButton(text: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text, color = Color.White)
    }
}