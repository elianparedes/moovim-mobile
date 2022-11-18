package com.moovim.ui.screens.main.routines

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import com.moovim.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRoutinesViewModel @Inject constructor(
    private val repository: RoutinesRepository
) : ViewModel() {

    var state by mutableStateOf(MyRoutinesState())

    init {
        getCurrentUserRoutines()
        getAllFavouriteRoutines()
    }

    fun getCurrentUserRoutines() {
        viewModelScope.launch {
            when (val response = repository.getCurrentUserRoutines()) {
                is Result.Success -> {
                    if (response.data != null) {
                        state = state.copy(userRoutines = response.data, isLoading = false)
                    }
                }
                is Result.Error -> {
                    state = state.copy(isError = true, isLoading = false)
                }
            }

        }
    }

    fun getAllFavouriteRoutines() {
        viewModelScope.launch {
            when (val response = repository.getAllFavouriteRoutines()) {
                is Result.Success -> {
                    if (response.data != null) {
                        state = state.copy(favouriteRoutines = response.data, isLoading = false)
                    }
                }
                is Result.Error -> {
                    state = state.copy(isError = true, isLoading = false)
                }
            }

        }
    }

    fun deleteRoutineFromFavourites(routineId: Int) {
        viewModelScope.launch {
            when (val result = repository.deleteRoutineFromFavourites(routineId)){
                is Result.Success -> {
                    state = state.copy(errorMessage= "Rutina borrada")
                }
                is Result.Error -> {
                    state = state.copy(errorMessage = "Sin conexión")
                }
            }
            state = state.copy(snackbar =true)
        }
    }

    fun addRoutineFromFavourites(routineId: Int) {
        viewModelScope.launch {
            when (val result = repository.addRoutineToFavourites(routineId)) {
                is Result.Success -> {
                    state = state.copy(errorMessage= "Rutina añadida")
                }
                is Result.Error -> {
                    if (result.code == 2){
                        state = state.copy(errorMessage= "La rutina ya fue añadida")
                    }
                    else{
                        state = state.copy(errorMessage= "Sin conexión")
                    }
                }
            }
            state = state.copy(snackbar = true)
        }
    }

    fun addRoutineReview(routineId: Int, score: Int, review: String) {
        viewModelScope.launch {
            when (val result = repository.addRoutineReview(routineId, score, review)){
                is Result.Success -> {
                    state = state.copy(errorMessage = "Rutina calificada")
                }
                is Result.Error -> {
                    state = state.copy(errorMessage = "Sin conexión")
                }
            }
            state = state.copy(snackbar = true)
        }
    }

    fun processFinished(){
        state = state.copy(snackbar = false)
    }

}