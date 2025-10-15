package com.example.looksoon.ui.screens.login_register.forgot_password

import androidx.compose.runtime.remember

data class ForgotPasswordState (
    val email: String = "",
    val isLoading: Boolean = false,
    val navigate: Boolean = false
)