package com.example.looksoon.ui.screens.login_register.login

data class LoginState (
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val navigate: Boolean = false
)