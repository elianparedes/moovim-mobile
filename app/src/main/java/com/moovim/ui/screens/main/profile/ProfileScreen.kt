package com.moovim.ui.screens.main.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.moovim.R
import com.moovim.ui.components.MoovimButton
import com.moovim.ui.nav.graphs.AuthScreen
import com.moovim.ui.nav.graphs.Graph

@Composable
fun ProfileScreen(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogoutClick: () -> Unit
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (state.avatarUrl.contains("http")) {
                AsyncImage(
                    model = state.avatarUrl,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                )
            } else {
                Image(
                    painterResource(id = R.drawable.ic_round_person),
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape)
                )
            }
        }
        Text(text = stringResource(id = R.string.account), style = MaterialTheme.typography.h3)
        ReadInputTextField(state.username, {}, stringResource(id = R.string.username_input_label))
        ReadInputTextField(state.email, {}, stringResource(id = R.string.email_input_label))
        MoovimButton({
                    viewModel.logout()
                    onLogoutClick()
                     }, stringResource(id = R.string.logout))
    }
}

@Composable
fun ReadInputTextField(text: String, onValueChangeText: (String) -> Unit, labelText: String) {

    OutlinedTextField(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
        readOnly = true,
        enabled = false,
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        value = text,
        onValueChange = onValueChangeText,
        label = { Text(labelText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
    )

}

