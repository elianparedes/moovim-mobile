package com.moovim.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MoovimButton( buttonOnClick: () -> Unit, buttonText: String){
    Button(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        onClick= buttonOnClick,
    ){
        Text(buttonText)
    }
}

@Composable
fun OutlinedMoovimButton(buttonOnClick: () -> Unit, buttonText: String){
    OutlinedButton(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        onClick = buttonOnClick,
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor= MaterialTheme.colors.background
            , contentColor = Color.White),
        border = BorderStroke(1.dp, Color.White)
    ){
        Text(buttonText)
    }
}
