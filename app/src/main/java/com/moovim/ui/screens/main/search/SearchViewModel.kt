package com.moovim.ui.screens.main.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.ExercisesRepository
import com.moovim.data.repository.RoutinesRepository
import com.moovim.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val routinesRepository: RoutinesRepository,
    private val exercisesRepository: ExercisesRepository,

    ) : ViewModel() {

    var state by mutableStateOf(SearchState())

    fun onQueryChange(query: TextFieldValue) {
        state = state.copy(query = query);
    }

    fun getAllRoutines(){
        if(!state.hasAllRoutines || state.orderChanged) {
            viewModelScope.launch {
                state = state.copy(isLoading = true)

                when (val response = routinesRepository.getAllRoutines(orderBy = state.orderBy, direction = state.direction, categoryId = state.categoryId)) {
                    is Result.Success -> {
                        if (response.data != null) {
                            state = state.copy(resultRoutines = response.data, isLoading = false)
                            state = state.copy(hasAllRoutines = true, orderChanged = false)
                        }
                    }

                    is Result.Error -> {
                        state = state.copy(isError = true)
                    }
                }
            }
        }
    }

    fun getRoutinesByCategory(categoryId: Int){
            viewModelScope.launch {
                state = state.copy(isLoading = true)

                when (val response = routinesRepository.getAllRoutines(categoryId = categoryId,orderBy = state.orderBy,direction = state.direction)) {
                    is Result.Success -> {
                        if (response.data != null) {
                            state = state.copy(resultRoutines = response.data, isLoading = false)
                            state = state.copy(hasAllRoutines = false)
                        }
                    }

                    is Result.Error -> {
                        state = state.copy(isError = true)
                    }
                }
            }
    }

    fun search(query: TextFieldValue) {
        if (query.text.isEmpty()) {
            getAllRoutines()
        } else {
            viewModelScope.launch {
                state = state.copy(isLoading = true)

                when (val response = routinesRepository.getAllRoutines(
                    query.text,
                    categoryId = state.categoryId,
                    orderBy = state.orderBy,
                    direction = state.direction
                )) {
                    is Result.Success -> {
                        if (response.data != null)
                            state = state.copy(resultRoutines = response.data, isLoading = false)
                        state = state.copy(hasAllRoutines = false)
                    }
                    is Result.Error -> {
                        state = state.copy(isError = true)
                    }
                }
            }
        }
    }

    fun orderByChange(orderBy: String, direction: String){
        state = state.copy(orderBy = orderBy, direction = direction, orderChanged = true)
        search(state.query)
    }

    fun categoryChange(categoryId: Int?){
        state = state.copy(categoryId = categoryId, orderChanged = true)
        search(state.query)
    }

    fun deleteRoutineFromFavourites(routineId: Int) {
        viewModelScope.launch {
            when (val result = routinesRepository.deleteRoutineFromFavourites(routineId)){
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
            when (val result = routinesRepository.addRoutineToFavourites(routineId)) {
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
            when (val result = routinesRepository.addRoutineReview(routineId, score, review)){
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

    fun updateSelectedReviewId(routineReviewId: Int){
        state = state.copy(selectedReviewId = routineReviewId)
    }

    fun openDisplay(){
        state = state.copy(sheetDisplay = true)
    }

    fun closeDisplay(){
        state = state.copy(sheetDisplay = false)
    }
}
