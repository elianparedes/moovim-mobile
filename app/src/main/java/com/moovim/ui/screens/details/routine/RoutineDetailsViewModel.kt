package com.moovim.ui.screens.details.routine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import com.moovim.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
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
            when(val response = routinesRepository.getRoutineDetails(routineId)){
                is Result.Success -> {
                    val routineDetails = response.data

                    if (routineDetails != null) {
                        val routine = routineDetails.routine
                        val cycles = routineDetails.cycles

                        state = state.copy(
                            name = routine.name,
                            detail = routine.detail,
                            imageUrl = routine.imageUrl,
                            author = routine.author,
                            avatarUrl = routine.avatarUrl,
                            cycles = cycles,
                            isLoading = false
                        )
                    }
                }

                is Result.Error -> {
                    state = state.copy(isError = true, isLoading = false)
                }
            }
        }
    }

}