package com.moovim.ui.screens.main.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import com.moovim.domain.model.UserRoutine
import com.moovim.util.Response
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
            when(val response = routinesRepository.getCurrentUserRoutines()){
                is Response.Success -> {
                    if (response.data != null){
                        val routines = response.data
                        state = state.copy(routines = routines, isLoading = false)
                        onRoutineSelect(routines[0].id, 0)
                    }

                }

                is Response.Error -> {
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

            state = state.copy(isLoading = true)
            when (val response = routinesRepository.getRoutineCycles(routineId)){
                is Response.Success -> {
                    if (response.data != null){
                        state = state.copy(
                            cycles = response.data, isLoading = false
                        )
                    }
                }

                is Response.Error -> {
                    state = state.copy(isError = false, isLoading = false)
                }
            }
        }
    }

}