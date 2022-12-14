package com.moovim.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moovim.ui.components.MoovimButton
import androidx.hilt.navigation.compose.hiltViewModel
import com.moovim.R
import com.moovim.ui.components.PasswordTextField

@Composable
fun LoginPasswordScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state

    if (state.isLoggedIn) {
        LaunchedEffect(Unit) {
            onLoginSuccess()
        }
    }
    val color : Color = MaterialTheme.colors.primary
    var textFieldColor: Color by remember { mutableStateOf(color) }
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
            stringResource(id = R.string.log_in_msg),
            modifier = Modifier
                .padding(bottom = 16.dp, start = 24.dp)
                .align(Alignment.Start),
            style = MaterialTheme.typography.h3,
            color = Color.White)
        Text(
            stringResource(id = R.string.moovim_mkting_msg),
            modifier = Modifier
                .padding(bottom = 16.dp, start = 24.dp, end = 24.dp)
                .align(Alignment.Start),
            style = MaterialTheme.typography.body1,
            color = Color.White)
        PasswordTextField(state.password, { newValue -> viewModel.onPasswordChange(newValue)},
            stringResource(id = R.string.password),textFieldColor)
        if (state.isError){
            textFieldColor = MaterialTheme.colors.error
            Text(
                text = stringResource(id = state.errorMessageId),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body2,
            )
        }
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Surface(
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colors.background
        ){
            MoovimButton({viewModel.login(state.user, state.password)}, stringResource(id = R.string.log_in))
        }
    }
}

