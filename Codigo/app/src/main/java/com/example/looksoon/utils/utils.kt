package com.example.looksoon.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.looksoon.model.SmartTool

fun getSmartToolsForRole(role: String): List<SmartTool> {
    return when (role) {
        "Artista" -> listOf(
            SmartTool("Tour Tracker Inteligente", Icons.Default.Place, "Seguimiento de giras", "tour_tracker"),
            SmartTool("Smart Story Creator", Icons.Default.Videocam, "Crea stories automáticas", "story_creator"),
            SmartTool("Performance Analyzer", Icons.Default.Mic, "Analiza presentaciones", "performance_analyzer")
        )
        "Fan" -> listOf(
            SmartTool("Smart Story Viewer", Icons.Default.PlayArrow, "Ver stories de artistas", "story_viewer"),
            SmartTool("Tour Tracker Viewer", Icons.Default.Public, "Sigue las giras", "tour_tracker")
        )
        "Curador" -> listOf(
            SmartTool("Tour Tracker Viewer", Icons.Default.Map, "Ver rutas de artistas", "tour_tracker"),
            SmartTool("Venue Ambience Detector", Icons.Default.Store, "Analiza locales", "venue_ambience")
        )
        "Local" -> listOf(
            SmartTool("Venue Ambience Detector", Icons.Default.LightMode, "Detecta ambiente del local", "venue_ambience"),
            SmartTool("Energy Level Monitor", Icons.Default.FitnessCenter, "Monitorea energía", "energy_monitor")
        )
        else -> emptyList()
    }
}
