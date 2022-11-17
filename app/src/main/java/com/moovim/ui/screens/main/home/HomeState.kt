package com.moovim.ui.screens.main.home

import com.moovim.domain.model.Cycle
import com.moovim.domain.model.Routine
import com.moovim.domain.model.UserRoutine

data class HomeState(
    val selectedRoutine: UserRoutine? = null,
    val selectedRoutineMenuIndex: Int = 0,
    val cycles: List<Cycle> = emptyList(),
    val routines: List<UserRoutine> = emptyList(),
    val isLoading: Boolean = true,
)