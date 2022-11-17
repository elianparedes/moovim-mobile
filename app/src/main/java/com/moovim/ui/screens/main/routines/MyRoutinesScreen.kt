package com.moovim.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.ui.components.RatingBar
import com.moovim.ui.components.RoutineCard
import com.moovim.ui.screens.main.routines.MyRoutinesViewModel

@Composable
fun RoutinesScreen(
    navController: NavHostController,
    viewModel: MyRoutinesViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var popupControl by remember { mutableStateOf(false) }
    var selectedId: Int by remember {mutableStateOf(0)}
    var score by remember { mutableStateOf(1) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (routine in state.userRoutines){
            RoutineCard(
                title = routine.name,
                description = routine.detail,
                author = routine.author,
                score = routine.score,
                imageUrl = routine.imageUrl,
                avatarUrl = routine.avatarUrl,
                onClickCard = { navController.navigate("routines/${routine.id}")},
                onShareClick = { /*TODO*/ },
                onFavClick = { viewModel.addRoutineFromFavourites(routine.id)},
                onScoreClick = { popupControl = true
                                selectedId = routine.id })
        }

        if (popupControl) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = {popupControl=false}
            ) {
                RatingBar(rating = score, onScoreChange = {newScore -> score=newScore}
                    , onPublishClick = { viewModel.addRoutineReview(selectedId, score, "")
                                        popupControl = false}
                )
            }
        }
    }
}
