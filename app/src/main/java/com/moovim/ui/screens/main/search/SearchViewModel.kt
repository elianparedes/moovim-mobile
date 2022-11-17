package com.moovim.ui.screens.main.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moovim.data.repository.ExercisesRepository
import com.moovim.data.repository.RoutinesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val routinesRepository: RoutinesRepository,
    private val exercisesRepository: ExercisesRepository,

): ViewModel() {

    var state by mutableStateOf(SearchState())

    fun onQueryChange(query: TextFieldValue){
        state = state.copy(query = query);
    }

    fun search(query: TextFieldValue){
        viewModelScope.launch {
            val routines = routinesRepository.getAllRoutines(query.text)
            //val exercises = exercisesRepository.getAllExercises(query.text)
            state = state.copy(resultRoutines = routines)
        }
    }

}