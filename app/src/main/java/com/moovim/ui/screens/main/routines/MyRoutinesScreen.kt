package com.moovim.ui.screens.main.routines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.ui.components.ChipSide
import com.moovim.ui.components.RatingBar
import com.moovim.ui.components.RoutineCard
import com.moovim.ui.components.SwitchChip

@Composable
fun RoutinesScreen(
    navController: NavHostController,
    viewModel: MyRoutinesViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var popupControl by remember { mutableStateOf(false) }
    var selectedId: Int by remember {mutableStateOf(0)}
    var score by remember { mutableStateOf(1) }
    var chipSide by rememberSaveable { mutableStateOf(ChipSide.LEFT) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SwitchChip(
            left = "Mis rutinas",
            right = "Favoritos",
            onLeft = { chipSide =  ChipSide.LEFT },
            onRight = { chipSide =  ChipSide.RIGHT  },
            chipSide = chipSide
        )
        if(chipSide == ChipSide.LEFT){
            viewModel.getCurrentUserRoutines()
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
                    favText = "Añadir a favoritos",
                    onFavClick = { viewModel.addRoutineFromFavourites(routine.id)},
                    onScoreClick = { popupControl = true
                        selectedId = routine.id })
            }
        }else
        {
            viewModel.getAllFavouriteRoutines()
            for (routine in state.favouriteRoutines){
                RoutineCard(
                    title = routine.name,
                    description = routine.detail,
                    author = routine.author,
                    score = routine.score,
                    imageUrl = routine.imageUrl,
                    avatarUrl = routine.avatarUrl,
                    onClickCard = { navController.navigate("routines/${routine.id}")},
                    onShareClick = { /*TODO*/ },
                    favText = "Quitar de favoritos",
                    onFavClick = { viewModel.deleteRoutineFromFavourites(routine.id)},
                    onScoreClick = { popupControl = true
                        selectedId = routine.id })
            }
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
