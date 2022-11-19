package com.moovim.ui.screens.main.routines

import com.moovim.domain.model.Routine
import com.moovim.domain.model.UserRoutine

data class MyRoutinesState(
    val userRoutines: List<Routine> = emptyList(),
    val favouriteRoutines: List<Routine> = emptyList(),
    val isLoading: Boolean = true,
    var snackbar: Boolean = false,
    var errorMessageId: Int = -1,
    var isError: Boolean = false,
    var loading: Boolean = false,
    var avatarUrl: String? = null
)