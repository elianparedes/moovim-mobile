package com.moovim.ui.screens.details

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.R
import com.moovim.domain.model.Cycle
import com.moovim.ui.components.ExerciseRoutineCard
import com.moovim.ui.components.NoContentCard
import com.moovim.ui.components.RoutineDetailedCard
import com.moovim.ui.screens.details.routine.RoutineDetailsViewModel
import com.moovim.ui.screens.main.Skeleton

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutineDetailsScreen(
    navController: NavHostController,
    routineId: Int,
    viewModel: RoutineDetailsViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val scrollState = rememberScrollState()

    Crossfade(targetState = state.isLoading) { isLoading ->
        if (isLoading) {
            SkeletonLoader()
        } else {
            Scaffold(topBar = {
                RoutineDetailedCard(
                    title = state.name,
                    description = state.detail,
                    author = state.author,
                    imageUrl = state.imageUrl,
                    avatarUrl = state.avatarUrl,
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
                        if (state.cycles.isNotEmpty())
                        {
                            state.cycles.forEach { cycle ->
                                CycleExercisesList(cycle)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        } else {
                            NoContentCard(stringResource(R.string.no_cycles_message))
                        }
                    }
                }

            }
        }
    }


}

@Composable
fun CycleExercisesList(cycle: Cycle) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                cycle.name,
                color = Color.White,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.h6
            )
            Icon(
                painter = painterResource(R.drawable.ic_repetitions),
                contentDescription = "repetitions_icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                cycle.repetitions.toString(),
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
        }

        if (cycle.cycleExercises.isNotEmpty()) {
            cycle.cycleExercises.forEach { cycleExercise ->
                ExerciseRoutineCard(title = cycleExercise.exercise.name,
                    group = cycleExercise.exercise.detail,
                    repetitions = cycleExercise.repetitions,
                    duration = cycleExercise.duration,
                    {})
            }
        } else {
            NoContentCard(stringResource(R.string.no_exercises_message))
        }

    }
}

@Composable
private fun SkeletonLoader() {
    Skeleton { loading ->
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp))
                    .background(loading)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                listOf(1, 2, 3, 4, 5, 6, 7, 8).forEach {
                    SkeletonExerciseCardLoader(loading = loading)
                }
            }
        }

    }
}

@Composable
fun SkeletonExerciseCardLoader(loading: Color) {
    Card(
        modifier = Modifier
            .height(128.dp)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(8.dp)
    )
    {
        Column(modifier = Modifier.fillMaxHeight()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 16.dp, 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(0.dp, 8.dp, 0.dp, 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth(0.7f)
                            .height(20.dp)
                            .background(loading)
                    )
                    Box(
                        modifier = Modifier
                            .padding(0.dp, 4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth(0.4f)
                            .height(16.dp)
                            .background(loading)
                    )
                }
            }
        }
    }
}