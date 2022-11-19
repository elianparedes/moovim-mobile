package com.moovim.ui.screens.auth;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moovim.R
import com.moovim.ui.components.OutlinedMoovimButton

@Composable
fun SignUpScreen(
    onContinueClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Text(
            stringResource(id = R.string.sign_up_msg),
            modifier = Modifier
                .padding(bottom = 16.dp, start = 24.dp)
                .align(Alignment.Start),
            style = MaterialTheme.typography.h3,
            color = Color.White)
        Text(
            stringResource(id = R.string.sign_up_motivational_msg),
            modifier = Modifier
                .padding(bottom = 16.dp, start = 24.dp)
                .align(Alignment.Start),
            style = MaterialTheme.typography.body1,
            color = Color.White)
        //InputTextField("Nombre de usuario")
        //InputTextField("Correo electrónico")
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Surface(
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colors.background
        ){
            OutlinedMoovimButton(onContinueClick, stringResource(id = R.string.continue_msg))
        }
    }
}


