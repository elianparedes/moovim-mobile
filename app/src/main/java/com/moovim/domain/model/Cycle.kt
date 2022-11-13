package com.moovim.domain.model

import com.moovim.data.remote.dto.CycleExerciseDto

data class Cycle(
    val id: Int,
    val name: String,
    val detail: String,
    val repetitions: Int,
    val order: Int,
    var cycleExercises: List<CycleExercise>
)