package com.moovim.ui.components

import android.graphics.Paint.Align
import android.icu.text.CaseMap.Title
import android.media.Image
import android.service.autofill.OnClickAction
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.moovim.R
import com.moovim.ui.theme.MoovimTheme
import com.moovim.ui.theme.Shapes

@Composable
fun MoovimButton(buttonOnClick: () -> Unit, buttonText: String) {
    Button(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        onClick = buttonOnClick,
    ) {
        Text(buttonText)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseCard(
    title: String,
    group: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(1.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(8.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5F)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
                    text = title,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    modifier = Modifier.padding(16.dp, 8.dp),
                    text = group,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(1F),
                horizontalAlignment = Alignment.End
            ) {
                Card(
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tricepspressdownn),
                        contentDescription = null
                    )
                }
            }

        }

    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun ExerciseCardPreview() {
    MoovimTheme {
        ExerciseCard("Ticeps en polea", "Triceps", {})
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutineCard(
    title: String,
    description: String,
    author: String,
    rating: Double,
    fav: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(1.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(8.dp)

    ) {
        Image(
            painter = painterResource(id = R.drawable.pexels_photo_1117493),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.drawWithCache {
                val gradient = Brush.horizontalGradient(
                    0.0f to Color(
                        0xFF252525
                    ), 0.3f to Color(0xE6252525), 1.0f to Color(0x80252525)
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient,
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                            setToSaturation(0F)
                        })
                    )
                }
            },
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0.8F) })
        )
        Column() {
            Row() {
                Column() {
                    Text(
                        modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 0.dp),
                        text = title,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        modifier = Modifier.padding(16.dp, 2.dp),
                        text = description,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(verticalArrangement = Arrangement.Bottom) {
                    Card(
                        shape = RoundedCornerShape(100),
                        modifier = Modifier
                            .fillMaxHeight(1F)
                            .padding(16.dp, 8.dp, 0.dp, 8.dp)
                            .aspectRatio(1F),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.images),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp, 2.dp),
                        text = author,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun RoutineCardPreview() {
    MoovimTheme {
        RoutineCard("Llegar al verano", "Perdida de peso", "Kim Wexler", 4.8, false, {})
    }
}


@Composable
fun OutlinedMoovimButton(buttonOnClick: () -> Unit, buttonText: String) {
    OutlinedButton(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        onClick = buttonOnClick,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = MaterialTheme.colors.background, contentColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Text(buttonText)
    }
}
