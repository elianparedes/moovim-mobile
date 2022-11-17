package com.moovim.ui.screens.main.routines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.ui.components.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutinesScreen(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    viewModel: MyRoutinesViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var popupControl by remember { mutableStateOf(false) }
    var selectedId: Int by remember {mutableStateOf(0)}
    var score by remember { mutableStateOf(1) }
    var chipSide by rememberSaveable { mutableStateOf(ChipSide.LEFT) }
    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

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
                    onClickCard = {
                        navController.navigate("routines/${routine.id}")},
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
            LaunchedEffect(Unit) {
                sheetState.show()
                popupControl=false
            }
        }

    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
            modifier = Modifier.padding(bottom=40.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
            RatingBar(rating = score, onScoreChange = {newScore -> score=newScore}
                , onPublishClick = {
                    viewModel.addRoutineReview(selectedId, score, "")
                    coroutineScope.launch {
                        sheetState.hide()
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "This is your message",
                        )
                    }
                }
            )
        } },
        modifier = Modifier.fillMaxSize()
    ) {}
}


