package com.moovim.ui.screens.player

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.moovim.R
import com.moovim.domain.model.CycleExercise
import com.moovim.ui.components.ExerciseRoutineCard
import com.moovim.ui.theme.NoRippleTheme
import com.moovim.ui.util.WindowInfo
import com.moovim.ui.util.rememberWindowInfo

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SimplePlayer(
    navController: NavHostController,
    id: Number,
    viewModel: SimplePlayerViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val windowInfo = rememberWindowInfo()

    if (state.currentCycle != null) {
        Scaffold(
            topBar = { TopBar(state.currentCycle.name) },
            bottomBar = {
                BottomPlayerBar(
                    { paused -> viewModel.setPaused(paused) },
                    state.paused,
                    { viewModel.skipNext() },
                    { viewModel.skipPrevious() },
                    { viewModel.restartTimer() },
                    { visible -> viewModel.setPlaylistVisible(visible) },
                    state.isPlaylistVisible,
                    windowInfo
                )
            }
        ) { paddingValues ->


            if (windowInfo.screenWidthInfo !is WindowInfo.WindowType.Expanded) {
                Crossfade(
                    targetState = state.isPlaylistVisible,
                ) { isPlaylistVisible ->
                    if (isPlaylistVisible && state.currentExercise != null) {
                        ExercisePlaylist(
                            state.currentExercise,
                            state.remainingExercises,
                            paddingValues
                        )
                    } else {
                        state.currentExercise?.let {
                            ExerciseView(
                                state.progress,
                                state.time,
                                it,
                                paddingValues,
                                windowInfo
                            )
                        }
                    }
                }
            } else {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 64.dp),
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Column(
                        Modifier
                            .weight(0.2f)
                            .fillMaxHeight(0.8f),
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        )
                    ) {
                        state.cycles.forEach { cycle ->

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier

                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                                    .then(
                                        if (state.currentCycle == cycle)
                                            Modifier.background(MaterialTheme.colors.secondary)
                                        else
                                            Modifier
                                    )
                                    .padding(24.dp)
                            ) {
                                Text(text = cycle.name, style = MaterialTheme.typography.h5)
                            }

                        }
                    }
                    Column(Modifier.weight(0.3f)) {
                        state.currentExercise?.let {
                            ExercisePlaylist(
                                it,
                                state.remainingExercises,
                            )
                        }
                    }
                    Column(Modifier.weight(0.5f)) {
                        state.currentExercise?.let {
                            ExerciseView(
                                state.progress,
                                state.time,
                                it,
                                paddingValues,
                                windowInfo
                            )
                        }
                    }

                }


            }
        }
    }

}

@Composable
fun TimeIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    time: String = String(),
    size: Int,
    stroke: Int,
    textSize: Int,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = FloatTweenSpec(1000, 0, FastOutSlowInEasing)
    )

    Column(modifier = modifier) {
        Box(
            Modifier
                .aspectRatio(1f)
                .padding(16.dp)
                .size(size.dp)
        ) {
            CircularProgressIndicator(
                progress = 100f,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.secondary,
                strokeWidth = stroke.dp,
            )

            CircularProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary,
                strokeWidth = stroke.dp,
            )

            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = time,
                    color = Color.White,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = textSize.sp,
                    fontFamily = FontFamily(Font(resId = R.font.inter_light))
                )
            }
        }
    }

}

@Composable
fun Animation(size: Int, modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_player_anim))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    Column(modifier = modifier) {
        Box(
            Modifier
                .aspectRatio(1f)
                .size(size.dp)
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
            )
        }
    }

}

@Composable
private fun BottomPlayerBar(
    onPause: (Boolean) -> Unit,
    isPaused: Boolean,
    onSkipNext: () -> Unit,
    onSkipPrevious: () -> Unit,
    onRestart: () -> Unit,
    onPlaylistMode: (Boolean) -> Unit,
    isInPlaylistMode: Boolean,
    windowInfo: WindowInfo
) {


    Row(
        modifier = Modifier
            .drawWithContent {
                val colors = listOf(Color.Transparent, Color(0xFF0F0F0F), Color(0xFF0F0F0F))
                drawRect(
                    brush = Brush.verticalGradient(colors),
                )
                drawContent()
            }
            .padding(vertical = 16.dp)
            .then(
                if (windowInfo.screenWidthInfo !is WindowInfo.WindowType.Expanded)
                    Modifier.fillMaxWidth()
                else
                    Modifier
                        .width(windowInfo.screenWidth / 2)
                        .absoluteOffset((windowInfo.screenWidth / 2) + 32.dp)
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (windowInfo.screenWidthInfo !is WindowInfo.WindowType.Expanded) {
            IconToggleButton(
                checked = isInPlaylistMode,
                onCheckedChange = { visible -> onPlaylistMode(visible) },
            ) {
                Icon(
                    painter = if (isInPlaylistMode) painterResource(R.drawable.ic_display) else painterResource(
                        R.drawable.ic_playlist
                    ),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }
        }

        IconButton(onClick = { onSkipPrevious() }) {
            Icon(
                painterResource(R.drawable.ic_skip_previous),
                contentDescription = "skip_previous",
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )
        }

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.Center
        ) {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                IconToggleButton(
                    checked = isPaused,
                    onCheckedChange = { paused -> onPause(paused) },
                ) {
                    Icon(
                        painter = if (isPaused) painterResource(R.drawable.ic_play) else painterResource(
                            R.drawable.ic_pause
                        ),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp),
                    )
                }
            }
        }

        IconButton(onClick = { onSkipNext() }) {
            Icon(
                painterResource(R.drawable.ic_skip_next),
                contentDescription = "skip_next",
                modifier = Modifier.size(48.dp),
                tint = Color.White,

                )
        }

        IconButton(onClick = { onRestart() }) {
            Icon(
                painterResource(R.drawable.ic_restart),
                contentDescription = "restart",
                modifier = Modifier.size(24.dp),
                tint = Color.White,

                )
        }

    }
}

@Composable
fun TopBar(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.h3)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExerciseView(
    progress: Float,
    timeText: String,
    currentExercise: CycleExercise,
    paddingValues: PaddingValues,
    windowInfo: WindowInfo
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(paddingValues)
    ) {

        Crossfade(targetState = currentExercise.duration) { duration ->
            if (duration > 0) {
                TimeIndicator(
                    size = 400,
                    stroke = if (windowInfo.screenWidthInfo !is WindowInfo.WindowType.Expanded) 8 else 12,
                    progress = progress,
                    time = timeText,
                    textSize = if (windowInfo.screenWidthInfo !is WindowInfo.WindowType.Compact) 98 else 120,
                )
            } else {
                Animation(size = 400)
            }
        }

        if (windowInfo.screenWidthInfo !is WindowInfo.WindowType.Expanded) {
            AnimatedContent(
                targetState = currentExercise,
                transitionSpec = {
                    // Compare the incoming number with the previous number.
                    if (targetState.order > initialState.order) {
                        // If the target number is larger, it slides up and fades in
                        // while the initial (smaller) number slides up and fades out.
                        slideInHorizontally { width -> width } + fadeIn() with
                                slideOutHorizontally { width -> -width } + fadeOut()
                    } else {
                        // If the target number is smaller, it slides down and fades in
                        // while the initial number slides down and fades out.
                        slideInHorizontally { width -> -width } + fadeIn() with
                                slideOutHorizontally { width -> width } + fadeOut()
                    }.using(
                        // Disable clipping since the faded slide-in/out should
                        // be displayed out of bounds.
                        SizeTransform(clip = false)
                    )
                }) { targetCycleExercise ->
                ExerciseRoutineCard(
                    title = targetCycleExercise.exercise.name,
                    group = targetCycleExercise.exercise.detail,
                    repetitions = targetCycleExercise.repetitions,
                    duration = targetCycleExercise.duration,
                    onClickCard = {})

            }
        }
    }
}

@Composable
fun ExercisePlaylist(
    currentExercise: CycleExercise,
    remainingExercises: List<CycleExercise>,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(paddingValues)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text("Actual")
        }

        ExerciseRoutineCard(
            title = currentExercise.exercise.name,
            group = currentExercise.exercise.detail,
            repetitions = currentExercise.repetitions,
            duration = currentExercise.duration,
            {})

        if (remainingExercises.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text("Restantes")
            }

            remainingExercises.forEach { cycleExercise ->
                ExerciseRoutineCard(
                    title = cycleExercise.exercise.name,
                    group = cycleExercise.exercise.detail,
                    repetitions = cycleExercise.repetitions,
                    duration = cycleExercise.duration,
                    {})
            }
        }
    }
}