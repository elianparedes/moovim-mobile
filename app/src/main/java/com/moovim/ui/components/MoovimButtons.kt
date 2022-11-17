package com.moovim.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
fun MoovimButtonModifier(modifier: Modifier, buttonOnClick: () -> Unit, buttonText: String) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        onClick = buttonOnClick,
    ) {
        Text(buttonText)
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

enum class ChipSide(){
    LEFT,
    RIGHT
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwitchChip(
    left: String,
    right: String,
    onLeft: ()->Unit,
    onRight: ()->Unit,
    chipSide: ChipSide
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(40.dp)) {
        if(chipSide == ChipSide.LEFT){
            Chip(onClick = {
                onLeft.invoke()
            },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black),
                modifier = Modifier.fillMaxWidth(0.45F)
            ) {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = left)
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.1F))
            Chip(onClick = {
                onRight.invoke()
            },
                border = BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant),
                colors = ChipDefaults.outlinedChipColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = right)
                }
            }
        } else
        {
            Chip(onClick = {
                onLeft.invoke()
            },
                border = BorderStroke(1.dp,MaterialTheme.colors.secondaryVariant),
                colors = ChipDefaults.outlinedChipColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White),
                modifier = Modifier.fillMaxWidth(0.45F)
            ) {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = left)
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.1F))
            Chip(onClick = {
                onRight.invoke()
            },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = right)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF181818)
@Composable
fun SwitchChipPreview() {
    MoovimTheme {
        var chipSide by rememberSaveable { mutableStateOf(ChipSide.LEFT) }
        Card(modifier = Modifier.fillMaxSize(), backgroundColor = MaterialTheme.colors.background) {
            Column(modifier =  Modifier.padding(16.dp), verticalArrangement = Arrangement.Center){
                SwitchChip(
                    left = "Categorias",
                    right = "Descubrir",
                    onLeft = { chipSide =  ChipSide.LEFT },
                    onRight = { chipSide =  ChipSide.RIGHT  },
                    chipSide = chipSide
                )
                if(chipSide == ChipSide.LEFT){
                    Card() {
                        Text(text = "En pantalla izquierda")
                    }
                }else
                {
                    Card() {
                        Text(text = "En pantalla derecha")
                    }
                }
            }
        }
    }
}