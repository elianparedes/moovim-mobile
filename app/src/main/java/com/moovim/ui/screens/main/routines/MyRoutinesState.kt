package com.moovim.ui.screens.main.routines

import com.moovim.domain.model.Routine

data class MyRoutinesState(
    val routines: List<Routine> = emptyList(),
)