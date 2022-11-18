package com.moovim.ui.screens.main.profile

data class ProfileState (
    val id: Number = 0,
    val email: String = "",
    val username: String = "",
    val firstName: String = "",
    val lastName : String = "",
    val avatarUrl : String = "",
    val gender : String = "",
    val isVerified: Boolean = false
)