package com.moovim.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.moovim.R
import com.moovim.ui.components.OutlinedMoovimButton

@Composable
fun SignUpPasswordScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
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
        //PasswordTextField("Contraseña")
        //PasswordTextField("Confirmar contraseña")
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Surface(
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colors.background
        ){
            OutlinedMoovimButton({}, stringResource(id = R.string.continue_msg))
        }
    }
}

