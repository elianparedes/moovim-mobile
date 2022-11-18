package com.moovim.ui.screens.auth

import com.moovim.domain.model.Routine

data class LoginState(
    val user: String = String(),
    val password: String = String(),
    val token: String? = String(),
    val isLoggedIn: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)