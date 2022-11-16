package com.moovim.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.moovim.R
import com.moovim.ui.theme.MoovimTheme

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
    fav: Boolean,
    imageUrl: String,
    avatarUrl: String,
    onClickCard: () -> Unit
) {
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
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.Top) {
                Column() {
                    Text(
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp , 4.dp),
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
                    Row( verticalAlignment = Alignment.CenterVertically) {
                        Text(text = score.toString(), color = MaterialTheme.colors.primary, style = MaterialTheme.typography.body2 )
                        Icon(
                            Icons.Rounded.Star,
                            null,
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.requiredSize(16.dp)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val aux  = remember {mutableStateOf(fav)}
                    IconToggleButton(
                        checked = aux.value,
                        onCheckedChange = {aux.value = !aux.value},
                    ) {
                        if(aux.value){
                            Icon(
                                painterResource(id = R.drawable.ic_favorite),
                                null,
                                tint = MaterialTheme.colors.onPrimary,
                            )
                        }
                        else {
                            Icon(
                                painterResource(id = R.drawable.ic_favorite_border),
                                null,
                                tint = MaterialTheme.colors.onPrimary,
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 0.dp, 8.dp),
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(
                            Icons.Rounded.MoreVert,
                            null,
                            tint = MaterialTheme.colors.onPrimary,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserRoutineCard(
    title: String,
    description: String,
    score: Int,
    fav: Boolean,
    imageUrl: String,
    onClickCard: () -> Unit
) {
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
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.Top) {
                Column() {
                    Text(
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp , 4.dp),
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
                    Row( verticalAlignment = Alignment.CenterVertically) {
                        Text(text = score.toString(), color = MaterialTheme.colors.primary, style = MaterialTheme.typography.body2 )
                        Icon(
                            Icons.Rounded.Star,
                            null,
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.requiredSize(16.dp)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val aux  = remember {mutableStateOf(fav)}
                    IconToggleButton(
                        checked = aux.value,
                        onCheckedChange = {aux.value = !aux.value},
                    ) {
                        if(aux.value){
                            Icon(
                                painterResource(id = R.drawable.ic_favorite),
                                null,
                                tint = MaterialTheme.colors.onPrimary,
                            )
                        }
                        else {
                            Icon(
                                painterResource(id = R.drawable.ic_favorite_border),
                                null,
                                tint = MaterialTheme.colors.onPrimary,
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 0.dp, 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        modifier = Modifier.padding(0.dp, 0.dp),
                        text = "Creado por ti",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(
                            Icons.Rounded.MoreVert,
                            null,
                            tint = MaterialTheme.colors.onPrimary,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun RoutineCardPreview() {
    MoovimTheme {
        Column(){
            RoutineCard("Llegar al verano", "Perdida de peso", "Kim Wexler", 0, false,
                "https://img.asmedia.epimg.net/resizer/X7QOAazpF59aDH6sTt2LayXuRaQ=/644x362/cloudfront-eu-central-1.images.arcpublishing.com/diarioas/ZZ5YGKHKCBCHHML6FISOF4HJWA.jpg" ,
                "https://static.wikia.nocookie.net/breakingbad/images/c/c1/4x11_-_Huell.png/revision/latest/scale-to-width-down/350?cb=20130913100459&path-prefix=es",{})
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
){
    Card(
        onClick = onClickCard,
        modifier = Modifier
            .height(128.dp)
            .fillMaxWidth()
            .padding(1.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(8.dp)

    )
    {
        Column(modifier = Modifier.fillMaxHeight()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp, 0.dp, 8.dp)) {
                Column() {
                    Text(
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 4.dp),
                        text = title,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onBackground
                    )

                    Text(
                        modifier = Modifier.padding(0.dp, 4.dp),
                        text = group,
                        style = MaterialTheme.typography.h6,
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
                if(repetitions!=null){
                    Column() {
                        Row(){
                            Icon(
                                painter = painterResource(id = R.drawable.ic_replay),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(8.dp,0.dp)
                            )
                            Text(text = repetitions.toString(),color = MaterialTheme.colors.onBackground)
                        }
                    }
                }
                if(duration!=null){
                    Column() {
                        Row(){
                            Icon(
                                painter = painterResource(id = R.drawable.ic_timer),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(8.dp,0.dp)
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
fun WideRoutineCard(
    title: String,
    description: String,
    author: String,
    imageUrl: String,
    avatarUrl: String,
    exercisesCount: Int,
    onClickArrow: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(200.dp)
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
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp, 16.dp, 0.dp, 0.dp),
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
                    .padding(24.dp, 4.dp, 0.dp, 16.dp),
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
            .fillMaxWidth()
            .padding(1.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        shape = RoundedCornerShape(0.dp,0.dp,8.dp,8.dp)

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
                    .padding(24.dp, 4.dp, 0.dp, 16.dp),
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

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun RoutineDetailedCardPreview() {
    MoovimTheme {
        Column(){
            RoutineDetailedCard("Llegar al verano", "Perdida de peso", "Kim Wexler",
                "https://img.asmedia.epimg.net/resizer/X7QOAazpF59aDH6sTt2LayXuRaQ=/644x362/cloudfront-eu-central-1.images.arcpublishing.com/diarioas/ZZ5YGKHKCBCHHML6FISOF4HJWA.jpg" ,
                "https://static.wikia.nocookie.net/breakingbad/images/c/c1/4x11_-_Huell.png/revision/latest/scale-to-width-down/350?cb=20130913100459&path-prefix=es",12,{})
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

@Composable
fun InputTextField(text: String, onValueChangeText: (String) -> Unit, labelText: String){

    OutlinedTextField(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        value = text,
        onValueChange = onValueChangeText,
        label = { Text(labelText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
    )

}

@Composable
fun PasswordTextField(text: String, onValueChangeText: (String) -> Unit, labelText: String){
    var passwordVisible by remember {mutableStateOf(false)}

    OutlinedTextField(
        modifier = Modifier.padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        value = text,
        onValueChange = onValueChangeText,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text(labelText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White),
        trailingIcon = {
            val image = if (passwordVisible)
                R.drawable.ic_round_visibility
            else R.drawable.ic_round_visibility_off

            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(painterResource(id = image), description)
            }
        }
    )
}
