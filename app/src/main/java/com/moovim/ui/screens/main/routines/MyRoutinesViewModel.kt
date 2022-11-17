package com.moovim.ui.screens.main.routines

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.RoutinesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRoutinesViewModel @Inject constructor(
    private val repository: RoutinesRepository
): ViewModel() {

        var state by mutableStateOf(MyRoutinesState())

        init {
            getCurrentUserRoutines()
            getAllFavouriteRoutines()
        }

        fun getCurrentUserRoutines(){
            viewModelScope.launch {
                val userRoutines = repository.getCurrentUserRoutines()
                state = state.copy(userRoutines = userRoutines)
            }
        }

        fun getAllFavouriteRoutines(){
            viewModelScope.launch {
                val favouriteRoutines = repository.getAllFavouriteRoutines()
                state = state.copy(favouriteRoutines = favouriteRoutines)
            }
        }

        fun deleteRoutineFromFavourites(routineId: Int){
            viewModelScope.launch {
                repository.deleteRoutineFromFavourites(routineId)
            }
        }

        fun addRoutineFromFavourites(routineId: Int){
            viewModelScope.launch {
                repository.addRoutineToFavourites(routineId)
            }
        }

        fun addRoutineReview(routineId: Int, score: Int, review: String){
            viewModelScope.launch {
                repository.addRoutineReview(routineId, score, review)
            }
        }

}