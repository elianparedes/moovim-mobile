package com.moovim.ui.screens.main

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.R
import com.moovim.ui.components.NoContentCard
import com.moovim.ui.components.WideRoutineCard
import com.moovim.ui.screens.details.CycleExercisesList
import com.moovim.ui.screens.details.SkeletonExerciseCardLoader
import com.moovim.ui.screens.main.home.HomeViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    setShowFab: (Boolean) -> Unit
) {
    val state = viewModel.state
    val scrollState = rememberScrollState()

    var expanded by remember {
        mutableStateOf(false)
    }

    setShowFab(state.cycles.isNotEmpty() && state.exerciseCount > 0)

    if (state.routines.isEmpty()){
        Column(
            Modifier
                .fillMaxSize()
                .padding(32.dp), verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically), horizontalAlignment = Alignment.CenterHorizontally) {

            Text(stringResource(R.string.no_routines_error), style = MaterialTheme.typography.h2, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.no_routines_message))
        }
    } else {
        Column(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box {
                    IconButton(onClick = {
                        expanded = true
                    }) {
                        Icon(
                            Icons.Rounded.ArrowDropDown,
                            "arrowDropDown",
                            Modifier.size(32.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.924f)
                            .height(232.dp)
                            .background(MaterialTheme.colors.secondary),
                        offset = DpOffset(x = 0.dp, y = 16.dp)
                    ) {
                        state.routines.forEachIndexed { itemIndex, routine ->
                            DropdownMenuItem(
                                onClick = {
                                    expanded = false
                                    viewModel.onRoutineSelect(routineId = routine.id, itemIndex)
                                },
                                modifier = if (itemIndex == state.selectedRoutineMenuIndex) Modifier.background(
                                    Color(
                                        0xff4f4f4f
                                    )
                                ) else Modifier
                            ) {
                                Text(text = routine.name)
                            }
                        }
                    }

                }

                if (state.selectedRoutine != null) {
                    Column {
                        Text(stringResource(R.string.current_routine_title), style = MaterialTheme.typography.body2)
                        Text(state.selectedRoutine.name, style = MaterialTheme.typography.h3)
                    }
                }

            }


            if (state.selectedRoutine != null) {
                WideRoutineCard(
                    title = state.selectedRoutine.name,
                    description = state.selectedRoutine.detail,
                    imageUrl = state.selectedRoutine.imageUrl,
                    exercisesCount = 10,
                    onClickCard = {}
                )

                if (state.isLoading) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Skeleton { loading ->
                            Spacer(modifier = Modifier.height(24.dp))
                            listOf(1, 2, 3, 4, 5, 6, 7, 8).forEach {
                                SkeletonExerciseCardLoader(loading = loading)
                            }
                        }

                    }
                } else {
                    if (state.cycles.isNotEmpty()) {
                        state.cycles.forEach { cycle ->
                            CycleExercisesList(cycle)
                        }
                    } else {
                        NoContentCard(stringResource(R.string.no_cycles_message))
                    }
                }
            }

            Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
        }
    }




}

@Composable
inline fun Skeleton(
    modifier: Modifier = Modifier,
    content: @Composable() ((value: Color) -> Unit)
) {
    val transition = rememberInfiniteTransition()

    val color = transition.animateColor(
        initialValue = Color(0xFF414141),
        targetValue = Color(0xFF2C2C2C),
        animationSpec = infiniteRepeatable(
            animation = tween(750, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    content(color.value)
}

