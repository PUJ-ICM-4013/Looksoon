package com.example.looksoon.ui.Audius

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.looksoon.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TracksViewModel : ViewModel() {

    private val api = AudiusApiService.create()

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks

    init {
        loadTrendingTracks()
    }

    private fun loadTrendingTracks() {
        viewModelScope.launch {
            try {
                val response = api.getTrendingTracks()
                _tracks.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
