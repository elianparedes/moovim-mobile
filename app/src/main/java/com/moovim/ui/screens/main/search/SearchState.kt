package com.moovim.ui.screens.main.search

import androidx.compose.ui.text.input.TextFieldValue
import com.moovim.domain.model.Exercise
import com.moovim.domain.model.Routine
import com.moovim.ui.components.ChipSide

data class SearchState(
    val resultExercises: List<Exercise> = emptyList(),
    val resultRoutines: List<Routine> = emptyList(),
    val query: TextFieldValue = TextFieldValue(),
    val orderBy: String = "date",
    val direction: String = "desc",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val hasAllRoutines: Boolean = false,
    val chipSide: ChipSide = ChipSide.LEFT,
    val categoryId: Int? = null,
    val orderChanged: Boolean = true,
    val filter: Boolean = false,
    var snackbar: Boolean = false,
    var errorMessage: String = "",
    var sheetDisplay: Boolean = false,
    val selectedReviewId: Int = 0
)