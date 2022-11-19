package com.moovim.ui.screens.main.routines

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.moovim.R
import com.moovim.ui.components.*
import com.moovim.ui.screens.main.Skeleton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutinesScreen(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    viewModel: MyRoutinesViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onProfileClick: () -> Unit
) {

    val state = viewModel.state
    var popupControl by remember { mutableStateOf(false) }
    var selectedId: Int by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(1) }
    var chipSide by rememberSaveable { mutableStateOf(ChipSide.LEFT) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(scrollState)
            .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Mis Rutinas", style = MaterialTheme.typography.h3)

            Box(){
                if (state.avatarUrl != null){
                    AsyncImage(
                        model = state.avatarUrl,
                        contentDescription = "profile image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(32.dp).clip(CircleShape).clickable { onProfileClick() },
                    )
                } else {
                    IconButton(onClick = { onProfileClick() }) {
                        Icon(
                            painterResource(R.drawable.ic_round_person),
                            contentDescription = "Perfil",
                            modifier = Modifier.size(32.dp),
                            tint = Color.White,
                        )
                    }
                }
            }
        }
        SwitchChip(
            left = stringResource(id = R.string.create_by_you),
            right = stringResource(id = R.string.favourites),
            onLeft = { chipSide = ChipSide.LEFT },
            onRight = { chipSide = ChipSide.RIGHT },
            chipSide = chipSide
        )
        if (chipSide == ChipSide.LEFT) {
            viewModel.getCurrentUserRoutines()

            if (viewModel.state.isLoading) {
                SkeletonLoader()
            } else {
                if (viewModel.state.userRoutines.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.nothing_here),
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.no_routines_by_you),
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
                            onShareClick = { shareRoutine(context, routine.id) },
                            favText = stringResource(id = R.string.add_to_fav),
                            onFavClick = { viewModel.addRoutineFromFavourites(routine.id) },
                            onScoreClick = {
                                popupControl = true
                                selectedId = routine.id
                            })
                    }
                    Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding() - 64.dp))
                }
            }
        } else {
            viewModel.getAllFavouriteRoutines()
            if (viewModel.state.isLoading) {
                SkeletonLoader()
            } else
                if (viewModel.state.favouriteRoutines.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.nothing_here),
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.no_favourites),
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
                            onShareClick = { shareRoutine(context, routine.id) },
                            favText = stringResource(id = R.string.rem_fav),
                            onFavClick = { viewModel.deleteRoutineFromFavourites(routine.id) },
                            onScoreClick = {
                                popupControl = true
                                selectedId = routine.id
                            })
                    }
                    Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding() - 64.dp))
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
                message = context.getString(state.errorMessageId),
            )
            viewModel.processFinished()
        }
    }
}

@Composable
private fun SkeletonLoader() {
    Skeleton { loading ->
        listOf(1, 2, 3, 4, 5, 8, 9).forEach {
            SkeletonRoutineCardLoader(loading = loading)
        }
    }
}

@Composable
fun SkeletonRoutineCardLoader(loading: Color) {
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

