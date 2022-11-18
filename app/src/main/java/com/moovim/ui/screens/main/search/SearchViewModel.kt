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
    private val exercisesRepository: ExercisesRepository
): ViewModel() {

    var state by mutableStateOf(SearchState())

    fun onQueryChange(query: TextFieldValue){
        state = state.copy(query = query);
    }

    fun search(query: TextFieldValue){
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            when(val response = routinesRepository.getAllRoutines(query.text)){
                is Result.Success -> {
                    if (response.data != null)
                        state = state.copy(resultRoutines = response.data, isLoading = false)
                }

                is Result.Error -> {
                    state = state.copy(isError = true)
                }
            }
        }
    }

}