package com.moovim.ui.screens.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import com.moovim.data.repository.UserRepository
import com.moovim.domain.model.Cycle
import com.moovim.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val routinesRepository: RoutinesRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    init {
        getRoutines()
    }

    private fun getRoutines() {
        viewModelScope.launch {
            when(val response = routinesRepository.getCurrentUserRoutines()){
                is Result.Success -> {
                    if (response.data != null){
                        val routines = response.data
                        state = state.copy(routines = routines, isLoading = false)

                        if (routines.isNotEmpty())
                            onRoutineSelect(routines[0].id, 0)

                    }

                }
                is Result.Error -> {
                    state = state.copy(isError = true)
                }
            }
        }
    }

    fun onRoutineSelect(routineId: Int, routineIndex: Int) {
        viewModelScope.launch {
            val routine = state.routines[routineIndex]

            state = state.copy(
                selectedRoutine = routine,
                selectedRoutineMenuIndex = routineIndex
            )

            userRepository.setUserCurrentRoutineId(routineId)

            state = state.copy(isLoading = true)
            when (val response = routinesRepository.getRoutineCycles(routineId)){
                is Result.Success -> {
                    if (response.data != null){
                        val cycles = response.data
                        state = state.copy(
                            cycles = cycles, isLoading = false, exerciseCount = getExercisesCount(cycles)
                        )
                    }
                }
                is Result.Error -> {
                    state = state.copy(isError = false, isLoading = false)
                }
            }
        }
    }

    fun getExercisesCount(cycles: List<Cycle>): Int{
        var exerciseCount = 0
        for (cycle in cycles)
            exerciseCount += cycle.cycleExercises.size
        return exerciseCount
    }

}