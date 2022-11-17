package com.moovim.ui.screens.main.routines

import com.moovim.domain.model.Routine
import com.moovim.domain.model.UserRoutine

data class MyRoutinesState(
    val userRoutines: List<Routine> = emptyList(),
    val favouriteRoutines: List<Routine> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)