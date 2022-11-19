package com.moovim.ui.screens.main.routines

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.R
import com.moovim.data.repository.RoutinesRepository
import com.moovim.data.repository.UserRepository
import com.moovim.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRoutinesViewModel @Inject constructor(
    private val repository: RoutinesRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(MyRoutinesState())

    init {
        getCurrentUser()
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
                    state = state.copy(errorMessageId= R.string.routine_removed)
                }
                is Result.Error -> {
                    state = state.copy(errorMessageId = R.string.rate_routine)
                }
            }
            state = state.copy(snackbar =true)
        }
    }

    fun addRoutineFromFavourites(routineId: Int) {
        viewModelScope.launch {
            when (val result = repository.addRoutineToFavourites(routineId)) {
                is Result.Success -> {
                    state = state.copy(errorMessageId = R.string.routine_add)
                }
                is Result.Error -> {
                    if (result.code == 2){
                        state = state.copy(errorMessageId = R.string.routine_add_error)
                    }
                    else{
                        state = state.copy(errorMessageId = R.string.no_connection)
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
                    state = state.copy(errorMessageId = R.string.rate_routine)
                }
                is Result.Error -> {
                    state = state.copy(errorMessageId = R.string.no_connection)
                }
            }
            state = state.copy(snackbar = true)
        }
    }

    fun processFinished(){
        state = state.copy(snackbar = false)
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            when (val response = userRepository.getCurrentUser()) {
                is Result.Success -> {
                    if (response.data != null) {
                        state = state.copy(avatarUrl = response.data.avatarUrl)
                    }
                }
                is Result.Error -> {
                    state = state.copy(isError = true, isLoading = false)
                }
            }

        }
    }

}