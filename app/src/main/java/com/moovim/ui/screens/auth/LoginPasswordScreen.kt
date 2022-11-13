package com.moovim.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moovim.ui.components.MoovimButton
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginPasswordScreen(
    onContinueClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
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
        Text("Inicia sesión en Moovim.",
            modifier = Modifier
                .padding(bottom = 16.dp, start = 24.dp)
                .align(Alignment.Start),
            style = MaterialTheme.typography.h3,
            color = Color.White)
        Text("Crear, compartir y buscar rutinas al alcance de tu mano.",
            modifier = Modifier
                .padding(bottom = 16.dp, start = 24.dp, end = 24.dp)
                .align(Alignment.Start),
            style = MaterialTheme.typography.body1,
            color = Color.White)
        PasswordTextField("Contraseña")
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Surface(
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colors.background
        ){
            MoovimButton(onContinueClick, "Iniciar sesión")
        }
    }
}