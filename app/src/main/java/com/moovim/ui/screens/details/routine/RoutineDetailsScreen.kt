package com.moovim.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.R
import com.moovim.domain.model.Cycle
import com.moovim.domain.model.Exercise
import com.moovim.ui.components.ExerciseRoutineCard
import com.moovim.ui.components.RoutineDetailedCard
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

    Scaffold(topBar = {
        RoutineDetailedCard(
            title = state.name,
            description = state.detail,
            author = state.author,
            imageUrl = state.imageUrl,
            avatarUrl = state.imageUrl,
            exercisesCount = 10, onClickArrow = { navController.popBackStack() }
        )
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                state.cycles.forEach { cycle ->
                    CycleExercisesList(cycle)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }

}

@Composable
private fun CycleExercisesList(cycle: Cycle) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(cycle.name, color = Color.White, modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_repetitions),
                contentDescription = "repetitions_icon"
            )
            Text(cycle.repetitions.toString(), color = Color.White)
        }

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