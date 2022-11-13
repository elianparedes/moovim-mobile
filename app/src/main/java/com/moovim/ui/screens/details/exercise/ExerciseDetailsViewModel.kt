package com.moovim.ui.screens.details.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.ExercisesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val exercisesRepository: ExercisesRepository
): ViewModel() {

    var state by mutableStateOf(ExerciseDetailsState())

    init {
        viewModelScope.launch {
            val exerciseId = savedStateHandle.get<Int>("exerciseId") ?: return@launch
            val getExercise = async { exercisesRepository.getExercise(exerciseId) }
            val exercise = getExercise.await()
            state = state.copy(name = exercise.name, detail = exercise.detail)
        }
    }


}