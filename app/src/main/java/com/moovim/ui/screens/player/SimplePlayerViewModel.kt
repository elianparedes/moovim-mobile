package com.moovim.ui.screens.player

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import com.moovim.data.repository.UserRepository
import com.moovim.domain.model.Cycle
import com.moovim.domain.model.CycleExercise
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

    private lateinit var exerciseIterator: ListIterator<CycleExercise>
    private lateinit var cycleIterator: ListIterator<Cycle>

    lateinit var countdown: ExtendedCountDownTimer

    init {
        getRoutineCycles()
    }

    private fun getRoutineCycles() {
        viewModelScope.launch {
            when (val response = routineRepository.getRoutineCycles(3)) {
                is Result.Success -> {
                    if (response.data != null) {
                        val cycles = response.data

                        exerciseIterator = cycles[0].cycleExercises.listIterator()
                        cycleIterator = cycles.listIterator()

                        state = state.copy(
                            cycles = cycles,
                            isLoading = false,
                            currentCycle = cycleIterator.next()
                        )

                        getNextExercise()
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
        if (exerciseIterator.hasNext()) {

            var nextExercise = exerciseIterator.next()
            if (nextExercise == state.currentExercise)
                if (exerciseIterator.hasNext())
                    nextExercise = exerciseIterator.next()
                else
                    return

            //countdown = newTimer(nextExercise.duration * 1000L)

            state = state.copy(currentExercise = nextExercise)
        } else {
            if (cycleIterator.hasNext()) {

                var nextCycle = cycleIterator.next()
                if (nextCycle == state.currentCycle)
                    if (cycleIterator.hasNext())
                        nextCycle = cycleIterator.next()
                    else
                        return

                exerciseIterator = nextCycle.cycleExercises.listIterator()

                val nextExercise = exerciseIterator.next()
                //countdown = newTimer(nextExercise.duration * 1000L)

                Log.d(TAG, "getNextExercise: " + nextCycle.name)
                state = state.copy(currentExercise = nextExercise, currentCycle = nextCycle)
            }
        }

    }

    private fun getPreviousExercise() {
        if (exerciseIterator.hasPrevious() && exerciseIterator.previousIndex() != 0) {

            var prevExercise = exerciseIterator.previous()
            if (prevExercise == state.currentExercise)
                if (exerciseIterator.hasPrevious())
                    prevExercise = exerciseIterator.previous()
                else {
                    Log.d(TAG, "getPreviousExercise: A")
                    return
                }


            //countdown = newTimer(prevExercise.duration * 1000L)

            state = state.copy(currentExercise = prevExercise)
        } else {
            if (cycleIterator.hasPrevious()) {
                var prevCycle = cycleIterator.previous()
                if (prevCycle == state.currentCycle)
                    if (cycleIterator.hasPrevious())
                        prevCycle = cycleIterator.previous()
                    else {
                        Log.d(TAG, "getPreviousExercise: B")
                        return
                    }
                exerciseIterator =
                    prevCycle.cycleExercises.listIterator(prevCycle.cycleExercises.size)

                val prevExercise = exerciseIterator.previous()
                //countdown = newTimer(prevExercise.duration * 1000L)

                Log.d(TAG, "getPreviousExercise: " + prevCycle.name)
                state = state.copy(currentExercise = prevExercise, currentCycle = prevCycle)
            }
        }
    }

    fun skipPrevious() {
        //countdown.restart()
        getPreviousExercise()
        //countdown.start()
    }

    fun skipNext() {
        //countdown.restart()
        getNextExercise()
        //countdown.start()
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