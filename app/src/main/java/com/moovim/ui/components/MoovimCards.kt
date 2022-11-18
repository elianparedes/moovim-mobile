package com.moovim.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.moovim.R
import com.moovim.ui.theme.MoovimTheme

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
                    style = MaterialTheme.typography.h6,
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutineCard(
    title: String,
    description: String,
    author: String,
    score: Int,
    imageUrl: String,
    avatarUrl: String,
    onClickCard: () -> Unit,
    onShareClick: () -> Unit,
    favText: String,
    onFavClick: () -> Unit,
    onScoreClick: () -> Unit
) {
    var menuOpen by remember { mutableStateOf(false) }
    Card(
        onClick = onClickCard,
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .padding(1.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(8.dp)

    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Foto de la rutina",
            contentScale = ContentScale.Crop,
            modifier = Modifier.drawWithCache {
                val gradient = Brush.horizontalGradient(
                    0.0f to Color(
                        0xFF252525
                    ), 0.3f to Color(0xE6252525), 1.0f to Color(0x80252525)
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        gradient,
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                            setToSaturation(0F)
                        })
                    )
                }
            },
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0.8F) })
        )
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column() {
                    Text(
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 4.dp),
                        text = title,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        modifier = Modifier.padding(0.dp, 2.dp),
                        text = description,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = score.toString(),
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.body2
                        )
                        Icon(
                            Icons.Rounded.Star,
                            null,
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.requiredSize(16.dp)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 0.dp, 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                if (author != "") {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.requiredSize(16.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(100),
                            modifier = Modifier
                                .aspectRatio(1F)
                                .requiredSize(16.dp)
                        ) {
                            if (avatarUrl != "") {
                                AsyncImage(
                                    model = avatarUrl,
                                    contentDescription = "Foto de perfil",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(8.dp),
                                    alignment = Alignment.BottomCenter
                                )
                            } else {
                                Image(
                                    painterResource(id = R.drawable.ic_round_person),
                                    contentDescription = "Foto de perfil",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(8.dp),
                                    alignment = Alignment.BottomCenter
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp, 0.dp),
                            text = author,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
                else {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp, 0.dp),
                            text = "Creado por ti",
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = {menuOpen = !menuOpen},
                    ) {
                        Icon(
                            Icons.Rounded.MoreVert,
                            contentDescription = "Dropdown menu",
                            tint = MaterialTheme.colors.onPrimary,
                        )
                        RoutineDropdown(menuOpen, {menuOpen = false}, onShareClick, favText, onFavClick, onScoreClick)
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseRoutineCard(
    title: String,
    group: String,
    repetitions: Int?,
    duration: Int?,
    onClickCard: () -> Unit,
) {
    Card(
        onClick = onClickCard,
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
                Column() {
                    Text(
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 4.dp),
                        text = title,
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                    )

                    Text(
                        modifier = Modifier.padding(0.dp, 4.dp),
                        text = group,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                if (repetitions != null) {
                    Column() {
                        Row() {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_replay),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(8.dp, 0.dp)
                            )
                            Text(
                                text = repetitions.toString(),
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                }
                if (duration != null) {
                    Column() {
                        Row() {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_timer),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(8.dp, 0.dp)
                            )
                            Text(text = duration.toString() ,color = MaterialTheme.colors.onBackground)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun ExerciseRoutineCardPreview() {
    MoovimTheme {
        ExerciseRoutineCard("Extension de tricep con polea","Triceps", 12,30,{ })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WideExerciseRoutineCard(
    title: String,
    group: String,
    repetitions: Int?,
    duration: Int?,
    onClickCard: () -> Unit,
) {
    Card(
        onClick = onClickCard,
        modifier = Modifier
            .height(180.dp)
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
                Column() {
                    Text(
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 4.dp),
                        text = title,
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                    )

                    Text(
                        modifier = Modifier.padding(0.dp, 4.dp),
                        text = group,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colors.secondaryVariant)
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                if (repetitions != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row() {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_replay),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(8.dp, 0.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.exercise_reps),
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        Row(horizontalArrangement = Arrangement.Center){
                            Text(text = repetitions.toString() ,color = MaterialTheme.colors.onBackground, modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
                if (duration != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row() {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_timer),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(8.dp, 0.dp)
                            )
                            Text(text = stringResource(id = R.string.exercise_sec),color = MaterialTheme.colors.onBackground)
                        }
                        Row(horizontalArrangement = Arrangement.Center){
                            Text(text = duration.toString() ,color = MaterialTheme.colors.onBackground,modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun WideExerciseRoutineCardPreview() {
    MoovimTheme {
        WideExerciseRoutineCard("Extension de tricep con polea","Triceps", 12,30,{ })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseDetailedRoutineCard(
    title: String,
    group: String,
    repetitions: Int?,
    duration: Int?,
    onClickCard: () -> Unit,
) {
    Card(
        onClick = onClickCard,
        modifier = Modifier
            .fillMaxHeight(0.5F)
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
                Column() {
                    Text(
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 4.dp),
                        text = title,
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        modifier = Modifier.padding(0.dp, 4.dp),
                        text = group,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6F)
                .padding(vertical = 8.dp)
            ) {
                Card(
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tricepspressdownn),
                        contentDescription = null,
                    )
                }

            }
            //Divider(modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp), color = MaterialTheme.colors.secondaryVariant)
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (repetitions != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row() {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_replay),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(8.dp, 0.dp)
                            )
                            Text(
                                text = stringResource(id = R.string.exercise_reps),
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        Row(horizontalArrangement = Arrangement.Center){
                            Text(text = repetitions.toString() ,color = MaterialTheme.colors.onBackground, modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
                if (duration != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row() {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_timer),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(8.dp, 0.dp)
                            )
                            Text(text = stringResource(id = R.string.exercise_sec),color = MaterialTheme.colors.onBackground)
                        }
                        Row(horizontalArrangement = Arrangement.Center){
                            Text(text = duration.toString() ,color = MaterialTheme.colors.onBackground,modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun ExerciseDetailedRoutineCardPreview() {
    MoovimTheme {
        ExerciseDetailedRoutineCard("Extension de tricep con polea","Triceps", 12,30,{ })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WideRoutineCard(
    title: String,
    description: String,
    author: String,
    imageUrl: String,
    avatarUrl: String,
    exercisesCount: Int,
    onClickCard: () -> Unit
) {
    ImageCard(
        modifier = Modifier
            .height(232.dp)
            .fillMaxWidth()
            .padding(1.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(8.dp),
        imageUrl = imageUrl,
        onClickCard = onClickCard
    ) {
        Column() {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
                verticalAlignment = Alignment.Top) {
                Column() {
                    Text(
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp , 8.dp),
                        text = title,
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onBackground
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp, 16.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_subject),
                            null,
                            tint = MaterialTheme.colors.onBackground,
                        )
                        Text(
                            modifier = Modifier.padding(4.dp,0.dp),
                            text = StringBuilder()
                                .append(exercisesCount.toString())
                                .append(" ")
                                .append(stringResource(id = R.string.routines_exercises_amount_message))
                                .toString(),
                            color = MaterialTheme.colors.onBackground, style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 16.dp, 16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.requiredSize(16.dp)) {
                    Card(
                        shape = RoundedCornerShape(100),
                        modifier = Modifier
                            .aspectRatio(1F)
                            .requiredSize(16.dp)
                    ){
                        if (avatarUrl == "") {
                            AsyncImage(
                                model = avatarUrl,
                                contentDescription = "Foto de perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(8.dp),
                                alignment = Alignment.BottomCenter
                            )
                        }
                        else {
                            Image (
                                painterResource(id = R.drawable.ic_round_person),
                                contentDescription = "Foto de perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(8.dp),
                                alignment = Alignment.BottomCenter
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp),
                        text = author,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageCard(
    modifier: Modifier,
    backgroundColor : Color,
    shape: Shape,
    imageUrl: String,
    onClickCard: () -> Unit,
    content: @Composable ()->Unit
){
    Card(
        modifier = modifier,
        backgroundColor = backgroundColor,
        shape = shape,
        onClick = onClickCard

    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Foto de la rutina",
            contentScale = ContentScale.Crop,
            modifier = Modifier.drawWithCache {
                val gradient = Brush.horizontalGradient(
                    0.0f to Color(
                        0xFF252525
                    ), 0.3f to Color(0xE6252525), 1.0f to Color(0x80252525)
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        gradient,
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                            setToSaturation(0F)
                        })
                    )
                }
            },
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0.8F) })
        )
        content.invoke()
    }
}

@Composable
fun RoutineDetailedCard(
    title: String,
    description: String,
    author: String,
    imageUrl: String,
    avatarUrl: String,
    exercisesCount: Int,
    onClickArrow: () -> Unit
){
    Card(
        modifier = Modifier
            .height(240.dp)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)

    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Foto de la rutina",
            contentScale = ContentScale.Crop,
            modifier = Modifier.drawWithCache {
                val gradient = Brush.horizontalGradient(
                    0.0f to Color(
                        0xFF252525
                    ), 0.3f to Color(0xE6252525), 1.0f to Color(0x80252525)
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(
                        gradient,
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                            setToSaturation(0F)
                        })
                    )
                }
            },
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0.8F) })
        )
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 4.dp, 0.dp, 0.dp),
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(
                    onClick = onClickArrow,
                ) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        null,
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 4.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.Top) {
                Column() {
                    Text(
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp , 8.dp),
                        text = title,
                        style = MaterialTheme.typography.h2,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onBackground
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp, 16.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_subject),
                            null,
                            tint = MaterialTheme.colors.onBackground,
                        )
                        Text(
                            modifier = Modifier.padding(4.dp,0.dp),
                            text = StringBuilder()
                                .append(exercisesCount.toString())
                                .append(" ")
                                .append(stringResource(id = R.string.routines_exercises_amount_message))
                                .toString(),
                            color = MaterialTheme.colors.onBackground,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 4.dp, 0.dp, 16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.requiredSize(16.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(100),
                        modifier = Modifier
                            .aspectRatio(1F)
                            .requiredSize(16.dp)
                    ){
                        if (avatarUrl == "") {
                            AsyncImage(
                                model = avatarUrl,
                                contentDescription = "Foto de perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(8.dp),
                                alignment = Alignment.BottomCenter
                            )
                        }
                        else {
                            Image (
                                painterResource(id = R.drawable.ic_round_person),
                                contentDescription = "Foto de perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(8.dp),
                                alignment = Alignment.BottomCenter
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp),
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
fun RoutineDetailedCardPreview() {
    MoovimTheme {
        Column() {
            RoutineDetailedCard("Llegar al verano",
                "Perdida de peso",
                "Kim Wexler",
                "https://img.asmedia.epimg.net/resizer/X7QOAazpF59aDH6sTt2LayXuRaQ=/644x362/cloudfront-eu-central-1.images.arcpublishing.com/diarioas/ZZ5YGKHKCBCHHML6FISOF4HJWA.jpg",
                "https://static.wikia.nocookie.net/breakingbad/images/c/c1/4x11_-_Huell.png/revision/latest/scale-to-width-down/350?cb=20130913100459&path-prefix=es",
                12,
                {})
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun WideRoutinePreview() {
    MoovimTheme {
        Column(){
            WideRoutineCard("Llegar al verano", "Perdida de peso", "Kim Wexler",
                "https://img.asmedia.epimg.net/resizer/X7QOAazpF59aDH6sTt2LayXuRaQ=/644x362/cloudfront-eu-central-1.images.arcpublishing.com/diarioas/ZZ5YGKHKCBCHHML6FISOF4HJWA.jpg" ,
                "https://static.wikia.nocookie.net/breakingbad/images/c/c1/4x11_-_Huell.png/revision/latest/scale-to-width-down/350?cb=20130913100459&path-prefix=es",12,{})
        }
    }
}

@Composable
fun ObjectiveCard(
    modifier: Modifier,
    title: String,
    description: String,
    imageUrl: String,
    onClickCard: () -> Unit
){
    ImageCard(
        modifier = modifier
            .height(240.dp)
            .width(300.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(8.dp),
        imageUrl = imageUrl,
        onClickCard = onClickCard
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp, 16.dp, 0.dp, 0.dp),
            horizontalAlignment = Alignment.Start,
        ) {
                Text(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp , 8.dp),
                    text = title,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    modifier= Modifier.padding(0.dp,0.dp,16.dp,0.dp),
                    text = description,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onBackground
                )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun ObjectiveCardPreview() {
    MoovimTheme {
        Column(){
            ObjectiveCard(
                modifier = Modifier,
                stringArrayResource(id = R.array.objectives_titles)[0],stringArrayResource(id = R.array.objectives_descriptions)[0],
                stringArrayResource(id = R.array.objectives_image_url)[0],{})
        }
    }
}

@Composable
fun MusclesCard(
    modifier: Modifier,
    title: String,
    imageUrl: String,
    onClickCard: () -> Unit
){
    ImageCard(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(8.dp),
        imageUrl = imageUrl,
        onClickCard = onClickCard
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp, 0.dp, 0.dp, 0.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp , 8.dp),
                text = title,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun MusclesCardPreview() {
    MoovimTheme {
        Column(){
            MusclesCard(
                modifier = Modifier,
                stringArrayResource(id = R.array.muscles_titles)[0],
                "https://images.pexels.com/photos/1552106/pexels-photo-1552106.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",{})
        }
    }
}


