package com.moovim.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        Card(
            onClick = {onClick()},
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
            modifier = Modifier.fillMaxWidth().height(64.dp)
        ){
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(16.dp)) {
                Text( text = "Registarse",
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    )
            }

        }
        Card(
            onClick = {onForgotClick()},
            modifier = Modifier.fillMaxWidth().height(64.dp)
        ){
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(16.dp)) {
                Text( text = "Recuperar contraseña",
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    )
            }

        }
    }

}