package com.moovim.ui.screens.main.routines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import com.moovim.util.Response
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
                is Response.Success -> {
                    if (response.data != null) {
                        state = state.copy(userRoutines = response.data, isLoading = false)
                    }
                }
                is Response.Error -> {
                    state = state.copy(isError = true, isLoading = false)
                }
            }

        }
    }

    fun getAllFavouriteRoutines() {
        viewModelScope.launch {
            when (val response = repository.getAllFavouriteRoutines()) {
                is Response.Success -> {
                    if (response.data != null) {
                        state = state.copy(favouriteRoutines = response.data, isLoading = false)
                    }
                }
                
                is Response.Error -> {
                    state = state.copy(isError = true, isLoading = false)
                }
            }

        }
    }

    fun deleteRoutineFromFavourites(routineId: Int) {
        viewModelScope.launch {
            repository.deleteRoutineFromFavourites(routineId)
        }
    }

    fun addRoutineFromFavourites(routineId: Int) {
        viewModelScope.launch {
            repository.addRoutineToFavourites(routineId)
        }
    }

    fun addRoutineReview(routineId: Int, score: Int, review: String) {
        viewModelScope.launch {
            repository.addRoutineReview(routineId, score, review)
        }
    }

}