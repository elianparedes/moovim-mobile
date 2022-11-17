package com.moovim.ui.screens.details.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.ExercisesRepository
import com.moovim.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    var state by mutableStateOf(ExerciseDetailsState())

    init {
        getExerciseDetails()
    }

    private fun getExerciseDetails() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val exerciseId = savedStateHandle.get<Int>("exerciseId") ?: return@launch
            when(val response = exercisesRepository.getExerciseDetails(exerciseId)){
                is Response.Success -> {
                    val exerciseDetails = response.data

                    if (exerciseDetails != null) {
                        val exercise = exerciseDetails.exercise
                        val images = exerciseDetails.images

                        state = state.copy(
                            name = exercise.name,
                            detail = exercise.detail,
                            pos = exercise.pos,
                            procedure = exercise.procedure,
                            images = images,
                            isLoading = false
                        )
                    }
                }

                is Response.Error -> {
                    state = state.copy(isError = true, isLoading = false)
                }
            }


        }
    }


}