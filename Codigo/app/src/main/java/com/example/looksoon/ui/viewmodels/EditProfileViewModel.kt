package com.example.looksoon.ui.viewmodels

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class EditProfileViewModel : ViewModel() {
    private val _selectedMediaUri = mutableStateOf<Uri?>(null)
    val selectedMediaUri: State<Uri?> = _selectedMediaUri

    fun setMediaUri(uri: Uri?) {
        _selectedMediaUri.value = uri
    }
}