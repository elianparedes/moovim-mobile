package com.moovim.domain.model

data class RoutineDetails(
    val routine: Routine,
    val cycles: List<Cycle>
)