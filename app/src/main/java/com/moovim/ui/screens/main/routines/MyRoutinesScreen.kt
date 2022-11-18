package com.moovim.ui.screens.main.routines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.R
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
    var selectedId: Int by remember { mutableStateOf(0) }
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
            .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Mis Rutinas", style = MaterialTheme.typography.h3)
            IconButton(onClick = {  }) {
                Icon(
                    painterResource(R.drawable.ic_round_person),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(44.dp),
                    tint = Color.White,
                    )
            }
        }
        SwitchChip(
            left = "Creado por ti",
            right = "Favoritos",
            onLeft = { chipSide = ChipSide.LEFT },
            onRight = { chipSide = ChipSide.RIGHT },
            chipSide = chipSide
        )
        if (chipSide == ChipSide.LEFT) {
            viewModel.getCurrentUserRoutines()
            if (viewModel.state.userRoutines.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Vaya! No hay nada por aquí...", style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Crea tu primer rutina para disfrutar de Moovim al máximo.",
                        style = MaterialTheme.typography.body1
                    )
                }
            } else {
                for (routine in state.userRoutines) {
                    RoutineCard(
                        title = routine.name,
                        description = routine.detail,
                        author = routine.author,
                        score = routine.score,
                        imageUrl = routine.imageUrl,
                        avatarUrl = routine.avatarUrl,
                        onClickCard = {
                            navController.navigate("routines/${routine.id}")
                        },
                        onShareClick = { /*TODO*/ },
                        favText = "Añadir a favoritos",
                        onFavClick = { viewModel.addRoutineFromFavourites(routine.id) },
                        onScoreClick = {
                            popupControl = true
                            selectedId = routine.id
                        })
                }
            }
        } else {
            viewModel.getAllFavouriteRoutines()
            if (viewModel.state.favouriteRoutines.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Vaya! No hay nada por aquí...", style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Agrega una rutina a Favoritos para poder acceder a ella más facilmente.",
                        style = MaterialTheme.typography.body1
                    )
                }
            } else {
                for (routine in state.favouriteRoutines) {
                    RoutineCard(
                        title = routine.name,
                        description = routine.detail,
                        author = routine.author,
                        score = routine.score,
                        imageUrl = routine.imageUrl,
                        avatarUrl = routine.avatarUrl,
                        onClickCard = { navController.navigate("routines/${routine.id}") },
                        onShareClick = { /*TODO*/ },
                        favText = "Quitar de favoritos",
                        onFavClick = { viewModel.deleteRoutineFromFavourites(routine.id) },
                        onScoreClick = {
                            popupControl = true
                            selectedId = routine.id
                        })
                }
            }
        }
        if (popupControl) {
            LaunchedEffect(Unit) {
                sheetState.show()
                popupControl = false
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                RatingBar(
                    rating = score,
                    onScoreChange = { newScore -> score = newScore },
                    onPublishClick = {
                        coroutineScope.launch {
                            viewModel.addRoutineReview(selectedId, score, "")
                            sheetState.hide()
                        }
                    }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {}

    if (state.snackbar) {
        LaunchedEffect(Unit) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = state.errorMessage,
            )
            viewModel.processFinished()
        }
    }
}

