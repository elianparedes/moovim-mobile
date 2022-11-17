package com.moovim.ui.screens.player

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import com.moovim.data.repository.UserRepository
import com.moovim.domain.model.CycleExercise
import com.moovim.util.ExtendedCountDownTimer
import com.moovim.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimplePlayerViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val routineRepository: RoutinesRepository
) : ViewModel() {

    val TAG = "SimplePlayerViewModel"

    var state by mutableStateOf(SimplePlayerState())

    lateinit var iterator: ListIterator<CycleExercise>
    lateinit var countdown: ExtendedCountDownTimer

    init {
        getRoutineCycles()
    }

    private fun getRoutineCycles() {
        viewModelScope.launch {
            when (val response = routineRepository.getRoutineCycles(1)) {
                is Response.Success -> {
                    if (response.data != null) {
                        val cycles = response.data

                        iterator = cycles[0].cycleExercises.listIterator()
                        state = state.copy(
                            cycles = cycles,
                            currentCycleExercise = iterator.next(),
                            isLoading = false
                        )
                        getNextExercise()
                    }
                }

                is Response.Error -> {
                    state = state.copy(isError = true, isLoading = false)
                }
            }


        }
    }

    private fun newTimer(duration: Long): ExtendedCountDownTimer {
        return object : ExtendedCountDownTimer(duration, 1000) {
            override fun onTimerTick(millisUntilFinished: Long) {
                state = state.copy(
                    progress = millisUntilFinished.toFloat() / duration,
                    time = String.format("%ds", millisUntilFinished / 1000)
                )
            }

            override fun onTimerFinish() {
                getNextExercise()
                countdown.start()
            }

        }

    }

    fun getNextExercise() {
        if (iterator.hasNext()) {
            val nextCycleExercise = iterator.next()
            Log.d(TAG, "getNextExercise: " + nextCycleExercise.exercise.name)
            state = state.copy(currentCycleExercise = nextCycleExercise)
            countdown = newTimer(nextCycleExercise.duration * 1000L)

        }
    }

    fun getPreviousExercise() {
        if (iterator.hasPrevious()) {
            val previousCycleExercise = iterator.previous()
            Log.d(TAG, "getPreviousExercise: " + previousCycleExercise.exercise.name)
            state = state.copy(currentCycleExercise = previousCycleExercise)
            countdown = newTimer(previousCycleExercise.duration * 1000L)

        }
    }

    fun skipPrevious() {
        countdown.restart()
        getPreviousExercise()
        countdown.start()
    }

    fun skipNext() {
        countdown.restart()
        getNextExercise()
        countdown.start()
    }

    fun setPaused(paused: Boolean) {
        if (paused) pauseTimer()
        else startTimer()
    }

    fun startTimer() {
        countdown.start()
        state = state.copy(paused = false)
    }


    fun pauseTimer() {
        countdown.pause()
        state = state.copy(paused = true)
    }

    fun restartTimer() {
        countdown.restart()
        state = state.copy(paused = false)
    }

    fun setPlaylistVisible(value: Boolean) {
        state = state.copy(isPlaylistVisible = value)
    }

}