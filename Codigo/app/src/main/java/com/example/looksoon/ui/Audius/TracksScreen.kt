package com.example.looksoon.ui.Audius

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.looksoon.model.Track

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TracksScreen(viewModel: TracksViewModel = viewModel()) {
    val tracks by viewModel.tracks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trending Songs (Audius)") }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(tracks) { track ->
                TrackItem(track = track)
            }
        }
    }
}

@Composable
fun TrackItem(track: Track) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = track.title, style = MaterialTheme.typography.titleMedium)
        Text(
            text = "Artista: ${track.user?.name ?: "Desconocido"}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
