package com.cesar.tiorico.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesar.tiorico.viewmodel.GameViewModel

// ── Paleta Casino Noir (misma que GameScreen) ─────────────────────────────────
private val CasinoBlack      = Color(0xFF0A0A0F)
private val CasinoDeepGreen  = Color(0xFF0D2B1A)
private val CasinoFelt       = Color(0xFF0F3D22)
private val CasinoGold       = Color(0xFFD4AF37)
private val CasinoGoldLight  = Color(0xFFF5D76E)
private val CasinoGoldDark   = Color(0xFF8B6914)
private val CasinoWhite      = Color(0xFFF5F0E8)
private val CasinoSilver     = Color(0xFFB0A080)
private val CasinoGreen      = Color(0xFF00C853)

private val fichaColores = listOf(
    Color(0xFFE53935),
    Color(0xFF1E88E5),
    Color(0xFF43A047),
    Color(0xFFFDD835),
    Color(0xFF8E24AA),
)

private val goldGradient = Brush.linearGradient(
    colors = listOf(CasinoGoldDark, CasinoGoldLight, CasinoGold, CasinoGoldDark)
)
private val feltGradient = Brush.radialGradient(
    colors = listOf(CasinoFelt, CasinoDeepGreen, Color(0xFF061A0E))
)

// ── Pantalla ──────────────────────────────────────────────────────────────────

@Composable
fun PlayerSetupScreen(
    viewModel: GameViewModel = viewModel(),
    onStart: () -> Unit
) {
    var cantidad by remember { mutableStateOf(2f) }
    val cantidadInt = cantidad.toInt()

    // Pulso infinito para el botón
    val pulso by rememberInfiniteTransition(label = "pulso").animateFloat(
        initialValue = 0.7f,
        targetValue  = 1f,
        animationSpec = infiniteRepeatable(
            animation  = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulsoAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(feltGradient),
        contentAlignment = Alignment.Center
    ) {
        // Patrón de puntos
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val spacing = 28.dp.toPx()
                    val dotR    = 1.5.dp.toPx()
                    var y = 0f
                    while (y < size.height) {
                        var x = 0f
                        while (x < size.width) {
                            drawCircle(
                                color  = Color.White.copy(alpha = 0.04f),
                                radius = dotR,
                                center = Offset(x, y)
                            )
                            x += spacing
                        }
                        y += spacing
                    }
                }
        )

        // ── Tarjeta central ───────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .shadow(20.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF0D1F14), Color(0xFF122A1C), Color(0xFF0D1F14))
                    )
                )
                .border(1.5.dp, goldGradient, RoundedCornerShape(20.dp))
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            // Ícono corona
            Text("🎲", fontSize = 48.sp, textAlign = TextAlign.Center)

            Spacer(Modifier.height(4.dp))

            Text(
                "NUEVA PARTIDA",
                style = TextStyle(
                    color        = CasinoGoldLight,
                    fontSize     = 20.sp,
                    fontWeight   = FontWeight.Black,
                    letterSpacing = 4.sp
                )
            )
            Text(
                "SELECCIONA TUS JUGADORES",
                style = TextStyle(
                    color        = CasinoSilver,
                    fontSize     = 9.sp,
                    letterSpacing = 2.5.sp
                )
            )

            Spacer(Modifier.height(24.dp))

            // Divisor dorado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, CasinoGoldDark, CasinoGoldLight, CasinoGoldDark, Color.Transparent)
                        )
                    )
            )

            Spacer(Modifier.height(28.dp))

            // Número grande animado
            Text(
                "$cantidadInt",
                style = TextStyle(
                    color      = CasinoGoldLight,
                    fontSize   = 72.sp,
                    fontWeight = FontWeight.Black
                )
            )
            Text(
                if (cantidadInt == 1) "JUGADOR" else "JUGADORES",
                style = TextStyle(
                    color        = CasinoSilver,
                    fontSize     = 11.sp,
                    letterSpacing = 3.sp,
                    fontWeight   = FontWeight.Medium
                )
            )

            Spacer(Modifier.height(24.dp))

            // Fichas de previsualización
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                fichaColores.take(5).forEachIndexed { index, color ->
                    val activa = index < cantidadInt
                    Box(
                        modifier = Modifier
                            .size(if (activa) 36.dp else 28.dp)
                            .shadow(if (activa) 6.dp else 0.dp, CircleShape)
                            .clip(CircleShape)
                            .background(if (activa) color else color.copy(alpha = 0.2f))
                            .border(
                                width = if (activa) 2.dp else 1.dp,
                                color = if (activa) Color.White.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.1f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (activa) {
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // Slider estilizado
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("2", "3", "4", "5").forEach { n ->
                        Text(
                            n,
                            style = TextStyle(
                                color      = if (n == cantidadInt.toString()) CasinoGoldLight else CasinoSilver.copy(alpha = 0.4f),
                                fontSize   = 11.sp,
                                fontWeight = if (n == cantidadInt.toString()) FontWeight.Black else FontWeight.Normal
                            )
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Slider(
                    value          = cantidad,
                    onValueChange  = { cantidad = it },
                    valueRange     = 2f..5f,
                    steps          = 2,
                    modifier       = Modifier.fillMaxWidth(),
                    colors         = SliderDefaults.colors(
                        thumbColor            = CasinoGoldLight,
                        activeTrackColor      = CasinoGold,
                        inactiveTrackColor    = CasinoGoldDark.copy(alpha = 0.3f),
                        activeTickColor       = CasinoBlack,
                        inactiveTickColor     = CasinoGoldDark.copy(alpha = 0.5f)
                    )
                )
            }

            Spacer(Modifier.height(28.dp))

            // Divisor
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, CasinoGoldDark, CasinoGoldLight, CasinoGoldDark, Color.Transparent)
                        )
                    )
            )

            Spacer(Modifier.height(24.dp))

            // Botón INICIAR
            Button(
                onClick = {
                    viewModel.iniciarJuego(cantidadInt, 5000)
                    onStart()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(12.dp, RoundedCornerShape(12.dp))
                    .border(
                        1.dp,
                        CasinoGoldLight.copy(alpha = pulso),
                        RoundedCornerShape(12.dp)
                    ),
                shape  = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFF7B6000), Color(0xFFF9A825), Color(0xFFFFD600), Color(0xFFF9A825))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "🎲  INICIAR PARTIDA  🎲",
                        style = TextStyle(
                            color        = CasinoBlack,
                            fontWeight   = FontWeight.Black,
                            fontSize     = 15.sp,
                            letterSpacing = 2.sp
                        )
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "META: $5,000 POR JUGADOR",
                style = TextStyle(
                    color        = CasinoSilver.copy(alpha = 0.5f),
                    fontSize     = 9.sp,
                    letterSpacing = 1.5.sp
                )
            )
        }
    }
}