package com.moovim.domain.model

import com.moovim.data.remote.dto.ExerciseDto
import com.moovim.data.remote.dto.toExercise

data class CycleExercise(
    val duration: Int,
    val exercise: Exercise,
    val order: Int,
    val repetitions: Int
)
