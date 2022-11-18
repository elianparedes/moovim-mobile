package com.moovim.ui.components

import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.moovim.R

@Composable
fun RoutineDropdown(expanded: Boolean, onDismissRequest: () -> Unit, onShareClick: () -> Unit, favText:String,
                    onFavClick: () -> Unit, onScoreClick: () -> Unit){
    DropdownMenu(expanded = expanded,
        onDismissRequest = onDismissRequest) {
        DropdownMenuItem(onClick = {onShareClick()
                                    onDismissRequest() }){
            Text("Compartir")
        }
        DropdownMenuItem(onClick = {onFavClick()
                                    onDismissRequest()}){
            Text(favText)
        }
        DropdownMenuItem(onClick = {onScoreClick()
                                    onDismissRequest()}){
            Text("Calificar")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    rating: Int,
    onScoreChange: (Int) -> Unit,
    onPublishClick: () -> Unit
) {
    var ratingState by remember {
        mutableStateOf(rating)
    }

    var selected by remember {
        mutableStateOf(false)
    }
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..5) {
                    Icon(
                        painter = if (i <= ratingState) painterResource(id = R.drawable.ic_baseline_star) else
                            painterResource(id = R.drawable.ic_baseline_star_border),
                        contentDescription = "star",
                        modifier = Modifier
                            .pointerInteropFilter {
                                when (it.action) {
                                    MotionEvent.ACTION_DOWN -> {
                                        selected = true
                                        ratingState = i
                                        onScoreChange(ratingState)
                                    }
                                    MotionEvent.ACTION_UP -> {
                                        selected = false
                                    }
                                }
                                true
                            }
                            .size(36.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            MoovimButtonModifier(Modifier.padding(top = 16.dp), onPublishClick, "Publicar")
        }
    }
}

@Composable
fun MoovimSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
){
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = {snackbarData ->
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = 8.dp
                    ){
                        Text(text = snackbarData.message, style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp))
                    }

                }
            },
        modifier = modifier
    )
}

fun shareRoutine(context: Context, routineId: Int){
    val type = "text/plain"
    val extraText = "https://moovim.app/$routineId"
    val shareWith = "Compartir rutina con..."

    val intent = Intent(Intent.ACTION_SEND)
    intent.type = type
    intent.putExtra(Intent.EXTRA_TEXT, extraText)

    ContextCompat.startActivity(
        context,
        Intent.createChooser(intent, shareWith),
        null
    )
}