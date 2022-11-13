package com.moovim.ui.screens.auth

import com.moovim.domain.model.Routine

data class LoginState(
    val token: String = String(),
    val isLoggedIn: Boolean = false
)