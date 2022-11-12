package com.moovim.ui.viewmodels

import com.moovim.domain.model.Routine

data class MyRoutinesState(
    val routines: List<Routine> = emptyList(),
)