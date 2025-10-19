package com.example.looksoon.ui.screens.smarttools.tourtracker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.looksoon.ui.theme.PurplePrimary
import com.example.looksoon.ui.theme.PurpleSecondary

@Composable
fun TourTrackerIntelligenceScreen(
    onBack: () -> Unit,
    onSmartToolSelected: (String) -> Unit
) {
    val vm: TourTrackerViewModel = viewModel()
    val data by vm.performanceData.collectAsState()

    LaunchedEffect(Unit) {
        vm.startTracking()
    }

    val animatedMovement = animateFloatAsState(
        targetValue = data.movementScore.toFloat(),
        animationSpec = tween(durationMillis = 1000)
    )
    val animatedEngagement = animateFloatAsState(
        targetValue = data.audienceEngagement.toFloat(),
        animationSpec = tween(durationMillis = 1200)
    )
    val animatedEnergy = animateFloatAsState(
        targetValue = data.energy.toFloat(),
        animationSpec = tween(durationMillis = 800)
    )

    Scaffold(

    ) { innerPadding ->

        Column (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            HeaderTourTracker(
                onBack = onBack,
                onAnalyzerClick = { onSmartToolSelected("performance_analyzer") }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Título animado
                Text(
                    text = "🎤 Tour Tracker Intelligence",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                // Tarjeta principal de métricas
                PerformanceMetricsCard(
                    movementScore = animatedMovement.value.toInt(),
                    energyScore = animatedEnergy.value.toInt(),
                    engagementScore = animatedEngagement.value.toInt(),
                    modifier = Modifier.fillMaxWidth()
                )

                // Tarjeta de datos del entorno
                EnvironmentDataCard(
                    lightIntensity = data.lightIntensity?.toInt() ?: 0,
                    soundLevel = data.soundLevel,
                    locationLat = data.locationLat,
                    locationLon = data.locationLon,
                    modifier = Modifier.fillMaxWidth()
                )

                // Botones de acción
                ActionButtons(
                    onBack = onBack,
                    onAnalyzerClick = { onSmartToolSelected("performance_analyzer") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }
}

@Composable
fun HeaderTourTracker(
    onBack: () -> Unit,
    onAnalyzerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        Color.Black
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón de volver
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(48.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.secondary
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }

            Text(
                text = "Tour Intelligence",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Botón de analyzer
            IconButton(
                onClick = onAnalyzerClick,
                modifier = Modifier
                    .size(48.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                PurplePrimary,
                                PurpleSecondary
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Analytics,
                    contentDescription = "Analizador",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun PerformanceMetricsCard(
    movementScore: Int,
    energyScore: Int,
    engagementScore: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(24.dp),
                clip = true
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Métricas de Performance",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            AnimatedMetricBar(
                label = "💪 Movimiento",
                value = movementScore,
                gradientColors = listOf(PurplePrimary, PurpleSecondary)
            )

            AnimatedMetricBar(
                label = "⚡ Energía",
                value = energyScore,
                gradientColors = listOf(Color(0xFFFFD700), Color(0xFFFFA500))
            )

            AnimatedMetricBar(
                label = "🎶 Engagement",
                value = engagementScore,
                gradientColors = listOf(Color(0xFF00B4DB), Color(0xFF0083B0))
            )
        }
    }
}

@Composable
fun AnimatedMetricBar(
    label: String,
    value: Int,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$value%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }

        // Barra de progreso con gradiente
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(value / 100f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = gradientColors
                        )
                    )
            )
        }
    }
}

@Composable
fun EnvironmentDataCard(
    lightIntensity: Int,
    soundLevel: Int,
    locationLat: Double?,
    locationLon: Double?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(20.dp),
                clip = true
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Datos del Entorno",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            EnvironmentDataRow(
                icon = "☀️",
                label = "Intensidad Lumínica",
                value = "$lightIntensity lx",
                modifier = Modifier.fillMaxWidth()
            )

            EnvironmentDataRow(
                icon = "🔊",
                label = "Nivel de Sonido",
                value = "$soundLevel dB",
                modifier = Modifier.fillMaxWidth()
            )

            EnvironmentDataRow(
                icon = "📍",
                label = "Ubicación",
                value = "${locationLat?.format(4) ?: "0.0000"}, ${locationLon?.format(4) ?: "0.0000"}",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun EnvironmentDataRow(
    icon: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun ActionButtons(
    onBack: () -> Unit,
    onAnalyzerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Botón Volver
        Button(
            onClick = onBack,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = "Volver",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        }

        // Botón Ir a Analyzer
        Button(
            onClick = onAnalyzerClick,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = "Analizador",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this)