package com.moovim.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moovim.ui.components.MoovimButton
import com.moovim.ui.components.OutlinedMoovimButton

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = 44.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom),
            )
        {
            Text(
                "Comienza con tus rutinas hoy.",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                MoovimButton({onClick()},"Iniciar sesión")
                OutlinedMoovimButton({onSignUpClick()}, "Crear cuenta")
                /*

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
        */
    }
}
