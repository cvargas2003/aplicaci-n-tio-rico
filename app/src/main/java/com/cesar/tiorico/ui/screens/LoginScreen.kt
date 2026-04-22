package com.cesar.tiorico.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Paleta Monopolio ──────────────────────────────────────────────────────────
private val MonopolyGreen      = Color(0xFF1B5E20)
private val MonopolyGreenLight = Color(0xFF2E7D32)
private val MonopolyGold       = Color(0xFFFFC107)
private val MonopolyGoldDark   = Color(0xFFF57F17)
private val MonopolyRed        = Color(0xFFB71C1C)
private val MonopolyIvory      = Color(0xFFFFFDE7)
private val MonopolyBrown      = Color(0xFF4E342E)
private val BoardBorder        = Color(0xFF33691E)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var usuario  by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error    by remember { mutableStateOf("") }

    // ── Fondo tablero ──────────────────────────────────────────────────────────
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(MonopolyGreenLight, MonopolyGreen, Color(0xFF0A3D0A)),
                    radius = 1200f
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        // Patrón de puntos sutil (tablero)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.03f), Color.Transparent),
                        radius = 40f
                    )
                )
        )

        // ── Tarjeta principal ──────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .shadow(16.dp, RoundedCornerShape(4.dp))
                .clip(RoundedCornerShape(4.dp))
                .background(MonopolyIvory)
                .border(4.dp, MonopolyGold, RoundedCornerShape(4.dp))
                .padding(4.dp),                    // espacio para borde doble
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Borde interior decorativo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, MonopolyBrown, RoundedCornerShape(2.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ── Cabecera ───────────────────────────────────────────────────
                Spacer(Modifier.height(8.dp))

                // Franja superior roja estilo Monopolio
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MonopolyRed),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MONOPOLIO",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 6.sp
                        )
                    )
                }

                Spacer(Modifier.height(12.dp))

                // Logo / título
                Text(
                    text = "💰",
                    fontSize = 52.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "TÍO RICO",
                    style = TextStyle(
                        color = MonopolyGreen,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 4.sp
                    )
                )
                Text(
                    text = "EL JUEGO DE LA FORTUNA",
                    style = TextStyle(
                        color = MonopolyBrown,
                        fontSize = 10.sp,
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(Modifier.height(24.dp))

                // ── Divisor decorativo ─────────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(modifier = Modifier.weight(1f), color = MonopolyGold, thickness = 1.5.dp)
                    Text(
                        "  ★  ",
                        color = MonopolyGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Divider(modifier = Modifier.weight(1f), color = MonopolyGold, thickness = 1.5.dp)
                }

                Spacer(Modifier.height(20.dp))

                // ── Campos de texto ────────────────────────────────────────────
                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = { Text("Usuario", color = MonopolyBrown) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor    = MonopolyGold,
                        unfocusedBorderColor  = MonopolyBrown.copy(alpha = 0.5f),
                        focusedLabelColor     = MonopolyGreenLight,
                        cursorColor           = MonopolyGreen,
                        focusedTextColor      = MonopolyBrown,
                        unfocusedTextColor    = MonopolyBrown
                    ),
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña", color = MonopolyBrown) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor    = MonopolyGold,
                        unfocusedBorderColor  = MonopolyBrown.copy(alpha = 0.5f),
                        focusedLabelColor     = MonopolyGreenLight,
                        cursorColor           = MonopolyGreen,
                        focusedTextColor      = MonopolyBrown,
                        unfocusedTextColor    = MonopolyBrown
                    ),
                    singleLine = true
                )

                Spacer(Modifier.height(24.dp))

                // ── Botón ──────────────────────────────────────────────────────
                Button(
                    onClick = {
                        if (usuario == "admin" && password == "1234") {
                            error = ""
                            onLoginSuccess()
                        } else {
                            error = "Credenciales incorrectas"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MonopolyGold,
                        contentColor   = MonopolyBrown
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text(
                        "🎲  INICIAR SESIÓN  🎲",
                        style = TextStyle(
                            fontWeight = FontWeight.Black,
                            fontSize   = 14.sp,
                            letterSpacing = 1.sp
                        )
                    )
                }

                // ── Error ──────────────────────────────────────────────────────
                if (error.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(MonopolyRed.copy(alpha = 0.1f))
                            .border(1.dp, MonopolyRed, RoundedCornerShape(4.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("⚠️  ", fontSize = 14.sp)
                        Text(
                            error,
                            color = MonopolyRed,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                // ── Pie decorativo ─────────────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(modifier = Modifier.weight(1f), color = MonopolyGold, thickness = 1.5.dp)
                    Text("  ★  ", color = MonopolyGold, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Divider(modifier = Modifier.weight(1f), color = MonopolyGold, thickness = 1.5.dp)
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    "© BANCO DE TÍO RICO S.A.",
                    style = TextStyle(
                        color = MonopolyBrown.copy(alpha = 0.5f),
                        fontSize = 9.sp,
                        letterSpacing = 1.5.sp
                    )
                )

                Spacer(Modifier.height(4.dp))
            }
        }
    }
}