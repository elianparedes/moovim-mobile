package com.moovim.ui.screens.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import com.moovim.domain.model.UserRoutine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val routinesRepository: RoutinesRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    init {
        getRoutines()
    }


    private fun getRoutines() {
        viewModelScope.launch {
            val routines = routinesRepository.getCurrentUserRoutines()

            state = state.copy(
                routines = routines
            )

            onRoutineSelect(routines[0].id, 0)
        }

    }

    fun onRoutineSelect(routineId: Int, routineIndex: Int) {
        viewModelScope.launch {

            val routine = state.routines[routineIndex]

            state = state.copy(
                selectedRoutine = UserRoutine(
                    id = routine.id,
                    name = routine.name,
                    detail = routine.detail,
                    imageUrl = routine.imageUrl,
                    score = routine.score
                ),
                selectedRoutineMenuIndex = routineIndex
            )

            state = state.copy(isLoading = true)
            val cycles = routinesRepository.getRoutineCycles(routineId)

            state = state.copy(
                cycles = cycles
            )
            state = state.copy(isLoading = false)
        }
    }

}