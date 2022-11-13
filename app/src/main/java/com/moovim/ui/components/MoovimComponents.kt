package com.moovim.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.moovim.R

@Composable
fun MoovimButton( buttonOnClick: () -> Unit, buttonText: String){
    Button(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        onClick= buttonOnClick,
    ){
        Text(buttonText)
    }
}

@Composable
fun OutlinedMoovimButton(buttonOnClick: () -> Unit, buttonText: String){
    OutlinedButton(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        onClick = buttonOnClick,
        colors = ButtonDefaults.outlinedButtonColors(backgroundColor= MaterialTheme.colors.background
            , contentColor = Color.White),
        border = BorderStroke(1.dp, Color.White)
    ){
        Text(buttonText)
    }
}

@Composable
fun InputTextField(text: String, onValueChangeText: (String) -> Unit, labelText: String){

    OutlinedTextField(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        value = text,
        onValueChange = onValueChangeText,
        label = { Text(labelText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(textColor = Color.White)
    )

}

@Composable
fun PasswordTextField(text: String, onValueChangeText: (String) -> Unit, labelText: String){
    var passwordVisible by remember {mutableStateOf(false)}

    OutlinedTextField(
        modifier = Modifier.padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        value = text,
        onValueChange = onValueChangeText,
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
