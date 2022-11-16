package com.moovim.domain.model

data class Routine(
    val id: Int,
    val name: String,
    val detail: String,
    val score: Int,
    val imageUrl: String,
    val author: String,
    val avatarUrl: String,
)
