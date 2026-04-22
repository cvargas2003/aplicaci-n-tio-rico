package com.cesar.tiorico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MetaScreen(
    onMetaSelected: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "🎯 Elige tu meta",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        MetaButton("$3,000 (Fácil)", Color(0xFF4CAF50)) {
            onMetaSelected(3000)
        }

        MetaButton("$5,000 (Normal)", Color(0xFFFFC107)) {
            onMetaSelected(5000)
        }

        MetaButton("$10,000 (Difícil)", Color(0xFFF44336)) {
            onMetaSelected(10000)
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