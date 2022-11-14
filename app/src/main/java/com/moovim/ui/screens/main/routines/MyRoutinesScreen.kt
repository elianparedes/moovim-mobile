package com.moovim.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.ui.components.RoutineCard
import com.moovim.ui.components.UserRoutineCard
import com.moovim.ui.screens.main.routines.MyRoutinesViewModel

@Composable
fun RoutinesScreen(navController: NavHostController, viewModel: MyRoutinesViewModel = hiltViewModel()) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.userRoutines.forEach { routine ->
                UserRoutineCard(
                    title = routine.name, description = routine.detail, score = routine.score,
                    fav = false, imageUrl = routine.imageUrl,
                    onClickCard = {navController.navigate("routines/${routine.id}")})
            }
            state.favouriteRoutines.forEach{
                routine ->
                RoutineCard(
                    title = routine.name,
                    description = routine.detail,
                    author = routine.author,
                    score = routine.score,
                    fav = true,
                    imageUrl = routine.imageUrl,
                    avatarUrl = routine.avatarUrl,
                    onClickCard = {navController.navigate("routines/${routine.id}")}
                )
            }
        }
    }
}
