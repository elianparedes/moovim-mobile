package com.moovim.ui.screens.details.routine

import com.moovim.domain.model.Cycle

data class RoutineDetailsState(
    val name: String = String(),
    val detail: String = String(),
    val imageUrl: String = String(),
    val author: String = String(),
    val avatarUrl: String = String(),
    val cycles: List<Cycle> = emptyList()
)