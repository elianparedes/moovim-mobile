package com.moovim.domain.model

data class Cycle(
    val id: Int,
    val name: String,
    val detail: String,
    val repetitions: Int,
    val order: Int,
)