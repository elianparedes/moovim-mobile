package com.moovim.ui.screens.player

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.moovim.ui.components.ExerciseRoutineCard
import com.moovim.ui.theme.NoRippleTheme

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SimplePlayer(
    navController: NavHostController,
    id: Number,
    viewModel: SimplePlayerViewModel = hiltViewModel()
) {

    val state = viewModel.state

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomPlayerBar(
                { paused -> viewModel.setPaused(paused) },
                state.paused,
                { viewModel.skipNext() },
                { viewModel.skipPrevious() },
                { viewModel.restartTimer() },
                { visible -> viewModel.setPlaylistVisible(visible) },
                state.isPlaylistVisible
            )
        }
    ) { paddingValues ->

        AnimatedVisibility(visible = !state.isPlaylistVisible, enter = fadeIn(), exit = fadeOut()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colors.background)
            ) {

                TimeIndicator(size = 400, stroke = 8, progress = state.progress, time = state.time)

                if (state.currentCycleExercise != null) {
                    AnimatedContent(targetState = state.currentCycleExercise, transitionSpec = {
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


    }

}


@Composable
fun TimeIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    time: String = String(),
    size: Int,
    stroke: Int,
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
                    fontSize = 98.sp,
                    fontFamily = FontFamily(Font(resId = R.font.inter_light))
                )
            }
        }
    }

}

@Composable
fun Loader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_anim_player))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}

@Composable
private fun BottomPlayerBar(
    onPause: (Boolean) -> Unit,
    isPaused: Boolean,
    onSkipNext: () -> Unit,
    onSkipPrevious: () -> Unit,
    onRestart: () -> Unit,
    onPlaylistMode: (Boolean) -> Unit,
    isInPlaylistMode: Boolean
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
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

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
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Calentamiento", style = MaterialTheme.typography.h3)
    }

}