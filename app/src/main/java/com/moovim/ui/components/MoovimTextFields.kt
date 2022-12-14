package com.moovim.ui.components

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
fun InputTextField(text: String, onValueChangeText: (String) -> Unit, labelText: String,
                   textFieldColor: Color = MaterialTheme.colors.primary) {

    OutlinedTextField(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        value = text,
        onValueChange = onValueChangeText,
        label = { Text(labelText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = textFieldColor,
            focusedLabelColor = textFieldColor, focusedBorderColor = textFieldColor, textColor = Color.White)
    )

}

@Composable
fun PasswordTextField(text: String, onValueChangeText: (String) -> Unit, labelText: String,
                      textFieldColor: Color = MaterialTheme.colors.primary) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        value = text,
        onValueChange = onValueChangeText,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text(labelText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = textFieldColor,
            focusedLabelColor = textFieldColor, focusedBorderColor = textFieldColor ,textColor = Color.White),
        trailingIcon = {
            val image = if (passwordVisible)
                R.drawable.ic_round_visibility
            else R.drawable.ic_round_visibility_off

            val description = if (passwordVisible) "Ocultar contrase??a" else "Mostrar contrase??a"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painterResource(id = image), description)
            }
        }
    )
}