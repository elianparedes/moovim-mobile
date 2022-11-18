package com.moovim.domain.model

data class User(
    val id: Number,
    val email: String,
    val username: String,
    val firstName: String = String(),
    val lastName: String= String(),
    val gender: String = String(),
    val avatarUrl: String = String(),
    val isVerified: Boolean,
)