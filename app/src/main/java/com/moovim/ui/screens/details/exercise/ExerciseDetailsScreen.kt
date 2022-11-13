package com.moovim.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.ui.screens.details.exercise.ExerciseDetailsViewModel

@Composable
fun ExerciseDetailsScreen(
    navController: NavHostController,
    exerciseId: Int,
    viewModel: ExerciseDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(state.name, color = Color.White)
        Text(state.detail, color = Color.White)
    }
}