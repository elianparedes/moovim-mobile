package com.moovim.domain.model

data class User(
    val id: Number,
    val username: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val avatarUrl: String,
    val isVerified: Boolean,
)