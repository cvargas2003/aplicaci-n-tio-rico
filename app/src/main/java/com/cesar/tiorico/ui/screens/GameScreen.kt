package com.cesar.tiorico.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cesar.tiorico.viewmodel.GameViewModel

// ── Paleta Casino Noir ────────────────────────────────────────────────────────
private val CasinoBlack      = Color(0xFF0A0A0F)
private val CasinoDeepGreen  = Color(0xFF0D2B1A)
private val CasinoFelt       = Color(0xFF0F3D22)
private val CasinoGold       = Color(0xFFD4AF37)
private val CasinoGoldLight  = Color(0xFFF5D76E)
private val CasinoGoldDark   = Color(0xFF8B6914)
private val CasinoRed        = Color(0xFFCC2200)
private val CasinoRedLight   = Color(0xFFFF4422)
private val CasinoAmber      = Color(0xFFFFAA00)
private val CasinoGreen      = Color(0xFF00C853)
private val CasinoWhite      = Color(0xFFF5F0E8)
private val CasinoSilver     = Color(0xFFB0A080)

// Fichas de jugadores
private val fichaColores = listOf(
    Color(0xFFE53935), // rojo
    Color(0xFF1E88E5), // azul
    Color(0xFF43A047), // verde
    Color(0xFFFDD835), // amarillo
    Color(0xFF8E24AA), // morado
    Color(0xFFFF7043), // naranja
)

private val goldGradient = Brush.linearGradient(
    colors = listOf(CasinoGoldDark, CasinoGoldLight, CasinoGold, CasinoGoldDark)
)

private val feltGradient = Brush.radialGradient(
    colors = listOf(CasinoFelt, CasinoDeepGreen, Color(0xFF061A0E))
)

// ── Componentes auxiliares ────────────────────────────────────────────────────

@Composable
fun GoldDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        CasinoGoldDark,
                        CasinoGoldLight,
                        CasinoGoldDark,
                        Color.Transparent
                    )
                )
            )
    )
}

@Composable
fun CasinoChip(color: Color, size: Dp = 28.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .shadow(4.dp, CircleShape)
            .clip(CircleShape)
            .background(color)
            .border(2.dp, Color.White.copy(alpha = 0.4f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size * 0.55f)
                .clip(CircleShape)
                .border(1.5.dp, Color.White.copy(alpha = 0.3f), CircleShape)
        )
    }
}

@Composable
fun NeonProgressBar(progress: Float, modifier: Modifier = Modifier) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(800, easing = EaseOutCubic),
        label = "progress"
    )

    val glowColor = when {
        progress < 0.35f -> CasinoRed
        progress < 0.7f  -> CasinoAmber
        else             -> CasinoGreen
    }

    val animatedGlow by animateColorAsState(
        targetValue = glowColor,
        animationSpec = tween(600),
        label = "glow"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.Black.copy(alpha = 0.6f))
            .border(1.dp, CasinoGoldDark.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            animatedGlow.copy(alpha = 0.7f),
                            animatedGlow,
                            Color.White.copy(alpha = 0.9f)
                        )
                    )
                )
        )
        // Marcas de ruleta
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(9) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(6.dp)
                        .background(Color.White.copy(alpha = 0.25f))
                )
            }
        }
    }
}

@Composable
fun StatBadge(label: String, value: String, icon: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black.copy(alpha = 0.35f))
            .border(1.dp, CasinoGoldDark.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(icon, fontSize = 16.sp)
        Text(
            value,
            style = TextStyle(
                color = CasinoGoldLight,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black
            )
        )
        Text(
            label,
            style = TextStyle(
                color = CasinoSilver,
                fontSize = 9.sp,
                letterSpacing = 1.sp
            )
        )
    }
}

// ── Pantalla principal ────────────────────────────────────────────────────────

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    onExit: () -> Unit = {}
) {
    val state   = viewModel.state
    val jugador = state.jugadores.getOrNull(state.jugadorActualIndex)
    val dinero  = jugador?.dinero ?: 0

    val dineroAnimado by animateIntAsState(
        targetValue = dinero,
        animationSpec = tween(700, easing = EaseOutExpo),
        label = "dinero"
    )
    val progresoAnimado by animateFloatAsState(
        targetValue = (dinero.toFloat() / state.meta).coerceIn(0f, 1f),
        animationSpec = tween(800, easing = EaseOutCubic),
        label = "progreso"
    )

    val colorDinero by animateColorAsState(
        targetValue = when {
            dinero <= 0              -> CasinoRedLight
            dinero < state.meta / 2  -> CasinoAmber
            else                     -> CasinoGreen
        },
        animationSpec = tween(500),
        label = "colorDinero"
    )

    // Pulso dorado infinito en el header
    val pulso by rememberInfiniteTransition(label = "pulso").animateFloat(
        initialValue = 0.6f,
        targetValue  = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulsoAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(feltGradient)
    ) {
        // Patrón de fondo: puntos estilo tapete
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ── HEADER ────────────────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(12.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF0D1F14),
                                    Color(0xFF122A1C),
                                    Color(0xFF0D1F14)
                                )
                            )
                        )
                        .border(
                            width = 1.5.dp,
                            brush = goldGradient,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {

                        // Turno
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val fichaIdx = state.jugadorActualIndex % fichaColores.size
                            CasinoChip(fichaColores[fichaIdx])
                            Text(
                                "TURNO DE  ${(jugador?.nombre ?: "-").uppercase()}",
                                style = TextStyle(
                                    color        = CasinoGoldLight,
                                    fontSize     = 13.sp,
                                    fontWeight   = FontWeight.Black,
                                    letterSpacing = 2.sp
                                )
                            )
                        }

                        Spacer(Modifier.height(14.dp))
                        GoldDivider()
                        Spacer(Modifier.height(14.dp))

                        // Dinero animado
                        Text(
                            "SALDO",
                            style = TextStyle(
                                color = CasinoSilver,
                                fontSize = 9.sp,
                                letterSpacing = 3.sp
                            )
                        )
                        Text(
                            "$ ${"%,d".format(dineroAnimado)}",
                            style = TextStyle(
                                color      = colorDinero,
                                fontSize   = 40.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        )

                        Spacer(Modifier.height(12.dp))

                        // Barra neon
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                "META",
                                style = TextStyle(
                                    color = CasinoSilver,
                                    fontSize = 9.sp,
                                    letterSpacing = 2.sp
                                )
                            )
                            NeonProgressBar(
                                progress  = progresoAnimado,
                                modifier  = Modifier.weight(1f)
                            )
                            Text(
                                "${(progresoAnimado * 100).toInt()}%",
                                style = TextStyle(
                                    color      = CasinoGoldLight,
                                    fontSize   = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(Modifier.height(14.dp))
                        GoldDivider()
                        Spacer(Modifier.height(12.dp))

                        // Stats row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatBadge("TURNO",  "${state.turno}",    "🔄")
                            StatBadge("META",   "$${"${state.meta}"}", "🎯")
                            StatBadge("JUGADORES", "${state.jugadores.size}", "👥")
                        }
                    }
                }
            }

            // ── ACCIÓN ACTUAL ─────────────────────────────────────────────────
            if (state.accionActual.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF1A3A2A),
                                        Color(0xFF0F2A1A)
                                    )
                                )
                            )
                            .border(
                                1.dp,
                                Brush.horizontalGradient(
                                    listOf(Color.Transparent, CasinoGoldDark, Color.Transparent)
                                ),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text("⚡", fontSize = 18.sp)
                            Text(
                                state.accionActual,
                                style = TextStyle(
                                    color      = CasinoWhite,
                                    fontSize   = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }

            // ── MENSAJE ───────────────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.4f))
                        .border(1.dp, CasinoSilver.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text  = state.mensaje,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color      = CasinoWhite.copy(alpha = 0.9f),
                            lineHeight = 22.sp
                        )
                    )
                }
            }

            // ── BOTONES DE ACCIÓN ─────────────────────────────────────────────
            if (!state.juegoTerminado) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        // AHORRAR
                        CasinoActionButton(
                            text      = "AHORRAR",
                            icon      = "🏦",
                            tagline   = "Guarda tu fortuna",
                            gradient  = Brush.linearGradient(
                                listOf(Color(0xFF1B5E20), Color(0xFF2E7D32), Color(0xFF43A047))
                            ),
                            accent    = CasinoGreen,
                            onClick   = { viewModel.ahorrar() }
                        )
                        // INVERTIR
                        CasinoActionButton(
                            text     = "INVERTIR",
                            icon     = "📈",
                            tagline  = "Arriesga para ganar",
                            gradient = Brush.linearGradient(
                                listOf(Color(0xFF7B6000), Color(0xFFF9A825), Color(0xFFFFD600))
                            ),
                            accent   = CasinoAmber,
                            onClick  = { viewModel.invertir() }
                        )
                        // GASTAR
                        CasinoActionButton(
                            text     = "GASTAR",
                            icon     = "🎰",
                            tagline  = "Vive el lujo",
                            gradient = Brush.linearGradient(
                                listOf(Color(0xFF7F0000), Color(0xFFB71C1C), Color(0xFFEF5350))
                            ),
                            accent   = CasinoRedLight,
                            onClick  = { viewModel.gastar() }
                        )
                    }
                }
            }

            // ── TABLERO DE JUGADORES ──────────────────────────────────────────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(18.dp)
                            .background(goldGradient, RoundedCornerShape(2.dp))
                    )
                    Text(
                        "TABLERO DE JUGADORES",
                        style = TextStyle(
                            color        = CasinoGoldLight,
                            fontSize     = 11.sp,
                            fontWeight   = FontWeight.Black,
                            letterSpacing = 2.5.sp
                        )
                    )
                }
            }

            items(state.jugadores) { p ->
                val esActual  = p == jugador
                val idx       = state.jugadores.indexOf(p)
                val chipColor = fichaColores[idx % fichaColores.size]

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(if (esActual) 8.dp else 2.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (esActual)
                                Brush.linearGradient(listOf(Color(0xFF0D2B1A), Color(0xFF163D24)))
                            else
                                Brush.linearGradient(listOf(Color(0xFF101010), Color(0xFF181818)))
                        )
                        .then(
                            if (esActual)
                                Modifier.border(1.dp, goldGradient, RoundedCornerShape(10.dp))
                            else
                                Modifier.border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(10.dp))
                        )
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            CasinoChip(chipColor, 24.dp)
                            Column {
                                Text(
                                    p.nombre.uppercase(),
                                    style = TextStyle(
                                        color        = if (esActual) CasinoGoldLight else CasinoWhite.copy(alpha = 0.7f),
                                        fontWeight   = if (esActual) FontWeight.Black else FontWeight.Normal,
                                        fontSize     = 12.sp,
                                        letterSpacing = 1.sp
                                    )
                                )
                                if (esActual) {
                                    Text(
                                        "EN JUEGO",
                                        style = TextStyle(
                                            color = CasinoGreen,
                                            fontSize = 8.sp,
                                            letterSpacing = 1.5.sp
                                        )
                                    )
                                }
                            }
                        }
                        Text(
                            "$ %,d".format(p.dinero),
                            style = TextStyle(
                                color      = if (esActual) CasinoGoldLight else CasinoSilver,
                                fontWeight = FontWeight.Bold,
                                fontSize   = 14.sp
                            )
                        )
                    }
                }
            }

            // ── RESULTADO FINAL ───────────────────────────────────────────────
            if (state.juegoTerminado) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(20.dp, RoundedCornerShape(20.dp))
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF3D2B00), Color(0xFF7A5500), Color(0xFFD4AF37))
                                )
                            )
                            .border(2.dp, CasinoGoldLight, RoundedCornerShape(20.dp))
                            .padding(28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("🏆", fontSize = 52.sp)
                            Text(
                                "GANADOR",
                                style = TextStyle(
                                    color        = CasinoBlack,
                                    fontSize     = 11.sp,
                                    fontWeight   = FontWeight.Black,
                                    letterSpacing = 5.sp
                                )
                            )
                            Text(
                                state.ganador.uppercase(),
                                style = TextStyle(
                                    color      = CasinoBlack,
                                    fontSize   = 28.sp,
                                    fontWeight = FontWeight.Black
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // ── SALIR ─────────────────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .border(1.dp, CasinoSilver.copy(alpha = 0.25f), RoundedCornerShape(10.dp))
                ) {
                    TextButton(
                        onClick  = onExit,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "🚪  ABANDONAR MESA",
                            style = TextStyle(
                                color        = CasinoSilver,
                                fontSize     = 12.sp,
                                letterSpacing = 1.5.sp,
                                fontWeight   = FontWeight.Medium
                            )
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

// ── Botón de acción de casino ─────────────────────────────────────────────────

@Composable
fun CasinoActionButton(
    text: String,
    icon: String,
    tagline: String,
    gradient: Brush,
    accent: Color,
    onClick: () -> Unit
) {
    Button(
        onClick  = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .border(1.dp, accent.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
        shape  = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(icon, fontSize = 24.sp)
                    Column {
                        Text(
                            text,
                            style = TextStyle(
                                color        = Color.White,
                                fontWeight   = FontWeight.Black,
                                fontSize     = 14.sp,
                                letterSpacing = 2.sp
                            )
                        )
                        Text(
                            tagline,
                            style = TextStyle(
                                color    = Color.White.copy(alpha = 0.65f),
                                fontSize = 9.sp,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }
                // Flecha decorativa
                Text(
                    "▶",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.4f),
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}