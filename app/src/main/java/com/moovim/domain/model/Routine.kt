package com.moovim.domain.model

import com.moovim.data.remote.dto.CategoryDto

data class Routine(
    val id: Int,
    val name: String,
    val detail: String,
    val score: Int,
    val imageUrl: String,
    val author: String,
    val avatarUrl: String,
    val category: Category
)
