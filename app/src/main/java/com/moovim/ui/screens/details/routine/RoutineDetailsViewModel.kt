package com.moovim.ui.screens.details.routine

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import com.moovim.domain.model.Routine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val routinesRepository: RoutinesRepository
) : ViewModel() {

    var state by mutableStateOf(RoutineDetailsState())

    init {
        viewModelScope.launch {
            val routineId = savedStateHandle.get<Int>("routineId") ?: return@launch

            val getRoutine = async {routinesRepository.getRoutine(routineId) }
            val routine = getRoutine.await();
            state = state.copy(name = routine.name, detail = routine.detail, imageUrl = routine.imageUrl, author = routine.author, avatarUrl = routine.avatarUrl)

            val getRoutineCycles = async {routinesRepository.getRoutineCycles(routineId)}
            val routineCycles = getRoutineCycles.await()
            state = state.copy(cycles = routineCycles)
        }
    }

}