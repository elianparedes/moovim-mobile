package com.moovim.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.moovim.domain.model.Cycle
import com.moovim.domain.model.Exercise
import com.moovim.ui.components.ExerciseRoutineCard
import com.moovim.ui.screens.details.routine.RoutineDetailsViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutineDetailsScreen(
    navController: NavHostController,
    routineId: Int,
    viewModel: RoutineDetailsViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val scrollState = rememberScrollState()

    Box(modifier = Modifier
        .verticalScroll(scrollState)
        .fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column() {
                Text(state.name, color = Color.White)
                Text(state.detail, color = Color.White)
                AsyncImage(
                    model = state.imageUrl,
                    contentDescription = state.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(16.dp)
                        )
                )
            }

            state.cycles.forEach { cycle ->
                CycleExercisesList(cycle)
            }
        }
    }

}

@Composable
private fun CycleExercisesList(cycle: Cycle) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(cycle.name, color = Color.White)
        cycle.cycleExercises.forEach { cycleExercise ->
            ExerciseRoutineCard(title = cycleExercise.exercise.name,
                group = cycleExercise.exercise.detail,
                repetitions = cycleExercise.repetitions,
                duration = cycleExercise.duration,
                {})
        }
    }
}

@Composable
private fun ExerciseCard(exercise: Exercise) {
    Card(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(exercise.name)
        }
    }
}