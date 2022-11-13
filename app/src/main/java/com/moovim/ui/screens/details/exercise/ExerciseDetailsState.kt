package com.moovim.ui.screens.details.exercise

import com.moovim.domain.model.ExerciseImage

data class ExerciseDetailsState(
    val name: String = String(),
    val detail: String = String(),
    val images: List<ExerciseImage> = emptyList(),
    val pos: String = String(),
    val procedure: String = String()
)
