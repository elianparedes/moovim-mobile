package com.moovim.ui.screens.main.routines

import com.moovim.domain.model.Routine
import com.moovim.domain.model.UserRoutine

data class MyRoutinesState(
    val userRoutines: List<UserRoutine> = emptyList(),
    val favouriteRoutines: List<Routine> = emptyList(),
)