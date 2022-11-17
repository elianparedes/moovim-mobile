package com.moovim.ui.screens.main.search

import androidx.compose.ui.text.input.TextFieldValue
import com.moovim.domain.model.Exercise
import com.moovim.domain.model.Routine

data class SearchState(
    val resultExercises: List<Exercise> = emptyList(),
    val resultRoutines: List<Routine> = emptyList(),
    val query: TextFieldValue = TextFieldValue(),
    val filters: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val hasAllRoutines: Boolean = false
)