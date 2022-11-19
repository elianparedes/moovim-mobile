package com.moovim.ui.screens.main.home

import android.net.RouteInfo
import com.moovim.domain.model.Cycle
import com.moovim.domain.model.Routine
import com.moovim.domain.model.UserRoutine

data class HomeState(
    val selectedRoutine: Routine? = null,
    val selectedRoutineMenuIndex: Int = 0,
    val cycles: List<Cycle> = emptyList(),
    val routines: List<Routine> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val exerciseCount: Int = 0
)