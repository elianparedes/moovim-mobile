package com.moovim.ui.screens.player

import android.os.CountDownTimer
import com.moovim.domain.model.Cycle
import com.moovim.domain.model.CycleExercise
import com.moovim.domain.model.Exercise

data class SimplePlayerState(
    val progress: Float = 0f,
    val time: String = "0s",
    val cycles: List<Cycle> = emptyList(),
    val paused: Boolean = true,
    val currentCycleExercise: CycleExercise? = null,
    val isPlaylistVisible: Boolean = false,
    val isLoading: Boolean = true,
    val isError: Boolean = false
)