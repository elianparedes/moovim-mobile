package com.moovim.ui.screens.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import com.moovim.data.repository.UserRepository
import com.moovim.domain.model.Cycle
import com.moovim.util.ExtendedCountDownTimer
import com.moovim.util.Result
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

    private var currentExerciseIndex = 0
    private var currentCycleIndex = 0

    lateinit var countdown: ExtendedCountDownTimer

    init {
        getRoutineCycles()
    }

    private fun getRoutineCycles() {
        val userCurrentRoutineId = userRepository.getUserCurrentRoutineId();
        if (userCurrentRoutineId == -1)
            return

        viewModelScope.launch {
            when (val response = routineRepository.getRoutineCycles(userCurrentRoutineId)) {
                is Result.Success -> {
                    if (response.data != null) {
                        val cycles = response.data

                        val firstExercise = cycles.first().cycleExercises.first()
                        if (firstExercise.duration > 0)
                            countdown = newTimer(firstExercise.duration * 1000L)

                        state = state.copy(
                            cycles = cycles,
                            isLoading = false,
                            currentCycle = cycles.first(),
                            currentExercise = cycles.first().cycleExercises.first(),
                            remainingExercises = cycles.first().cycleExercises.slice(
                                1..
                                        cycles.first().cycleExercises.lastIndex
                            )
                        )
                    }
                }

                is Result.Error -> {
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

    private fun getNextExercise() {
        if (state.currentCycle == null)
            return

        if (currentExerciseIndex < state.currentCycle!!.cycleExercises.lastIndex) {

            val nextExercise = state.currentCycle!!.cycleExercises[++currentExerciseIndex];

            if (nextExercise.duration > 0)
                countdown = newTimer(nextExercise.duration * 1000L)


            state =
                state.copy(
                    currentExercise = nextExercise,
                    remainingExercises = state.currentCycle!!.cycleExercises.slice(
                        currentExerciseIndex + 1..
                                state.currentCycle!!.cycleExercises.lastIndex
                    )
                )
        } else {
            if (currentCycleIndex < state.cycles.lastIndex) {

                lateinit var nextCycle: Cycle
                do{
                    nextCycle = state.cycles[++currentCycleIndex]
                } while (nextCycle.cycleExercises.isEmpty() && currentCycleIndex < state.cycles.lastIndex)

                val nextExercise = nextCycle.cycleExercises[0]
                currentExerciseIndex = 0

                state = state.copy(
                    currentCycle = nextCycle, currentExercise = nextExercise,
                    remainingExercises = nextCycle.cycleExercises.slice(
                        currentExerciseIndex + 1..nextCycle.cycleExercises.lastIndex
                    )
                )
            }
        }
    }

    private fun getPreviousExercise() {
        if (state.currentCycle == null)
            return

        if (currentExerciseIndex > 0) {
            val nextExercise = state.currentCycle!!.cycleExercises[--currentExerciseIndex]

            if (nextExercise.duration > 0)
                countdown = newTimer(nextExercise.duration * 1000L)

            state = state.copy(
                currentExercise = nextExercise,
                remainingExercises = state.currentCycle!!.cycleExercises.slice(
                    currentExerciseIndex + 1..
                            state.currentCycle!!.cycleExercises.lastIndex
                )
            )

        } else {
            if (currentCycleIndex > 0) {

                lateinit var nextCycle: Cycle
                do{
                    nextCycle = state.cycles[--currentCycleIndex]
                } while (nextCycle.cycleExercises.isEmpty() && currentCycleIndex > 0)

                currentExerciseIndex = nextCycle.cycleExercises.lastIndex
                val nextExercise = nextCycle.cycleExercises[currentExerciseIndex]

                if (nextExercise.duration > 0)
                    countdown = newTimer(nextExercise.duration * 1000L)

                state = state.copy(
                    currentCycle = nextCycle,
                    currentExercise = nextExercise,
                    remainingExercises = nextCycle.cycleExercises.slice(
                        currentExerciseIndex + 1..
                                nextCycle.cycleExercises.lastIndex
                    )
                )
            }
        }
    }

    fun skipPrevious() {
        restartTimer()
        getPreviousExercise()

        if (state.currentExercise!!.duration > 0)
            countdown = newTimer(state.currentExercise!!.duration * 1000L)
    }

    fun skipNext() {
        restartTimer()
        getNextExercise()

        if (state.currentExercise!!.duration > 0)
            countdown = newTimer(state.currentExercise!!.duration * 1000L)
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
        state = state.copy(paused = true, time = "0s", progress = 0f)
    }

    fun setPlaylistVisible(value: Boolean) {
        state = state.copy(isPlaylistVisible = value)
    }


}