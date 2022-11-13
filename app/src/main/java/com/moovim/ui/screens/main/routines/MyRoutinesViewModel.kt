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
        }

        private fun getCurrentUserRoutines(){
            viewModelScope.launch {
                val routines = repository.getCurrentUserRoutines()
                state = state.copy(routines = routines)
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

}