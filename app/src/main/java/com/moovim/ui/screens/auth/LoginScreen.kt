package com.moovim.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state = viewModel.state

    if (state.isLoggedIn){
        LaunchedEffect(Unit){
            onClick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        Card(
            onClick = {
                viewModel.login("usuario1", "1234567890")
                      },
            modifier = Modifier.fillMaxWidth()
        ){
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(16.dp)) {
                Text( text = "Iniciar Sesión",
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold)
            }
        }
        Card(
            onClick = {onSignUpClick()},
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ){
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(16.dp)) {
                Text( text = "Registarse",
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    )
            }

        }
        Card(
            onClick = {onForgotClick()},
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ){
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(16.dp)) {
                Text( text = "Recuperar contraseña",
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    )
            }

        }

    }

}