package com.moovim.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moovim.R
import com.moovim.ui.components.MoovimButton
import com.moovim.ui.components.OutlinedMoovimButton


@Composable
fun LoginScreen(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onHomeClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state = viewModel.state

    if (state.isLoggedIn){
        LaunchedEffect(Unit){
            onHomeClick()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Image(
                painterResource(id = R.drawable.moovim_logo),
                contentDescription = "Moovim Logo",
                modifier = Modifier.size(192.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    stringResource(id = R.string.log_in_motivational_msg),
                    modifier = Modifier.padding(24.dp, 8.dp, 24.dp, 16.dp),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                MoovimButton({ onClick() }, stringResource(id = R.string.log_in))
                //OutlinedMoovimButton({ onSignUpClick() }, "Crear cuenta")
            }
    }
}