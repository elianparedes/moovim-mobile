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
        Text("Regístrate en Moovim.",
            modifier = Modifier.padding(bottom = 16.dp, start=24.dp).
            align(Alignment.Start),
            style = MaterialTheme.typography.h3,
            color = Color.White)
        Text("Supérate a ti mismo.",
            modifier = Modifier.padding(bottom = 16.dp, start=24.dp).
            align(Alignment.Start),
            style = MaterialTheme.typography.body1,
            color = Color.White)
        PasswordTextField("Contraseña")
        PasswordTextField("Confirmar contraseña")
        Spacer(
            modifier = Modifier.weight(1f)
        )
        Surface(
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colors.background
        ){
            OutlinedMoovimButton({}, "Continuar")
        }
    }
}

@Composable
fun PasswordTextField(labelText: String){
    var text by remember { mutableStateOf("") }
    var passwordVisible by remember {mutableStateOf(false)}

    OutlinedTextField(
        modifier = Modifier.padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        value = text,
        onValueChange = { text = it },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text(labelText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White),
        trailingIcon = {
            val image = if (passwordVisible)
                R.drawable.ic_round_visibility
            else R.drawable.ic_round_visibility_off

            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(painterResource(id = image), description)
            }
        }
    )
}
