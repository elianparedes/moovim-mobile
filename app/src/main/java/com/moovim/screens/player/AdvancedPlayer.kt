package com.moovim.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.moovim.nav.graphs.PlayerScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdvancedPlayer(navController: NavHostController, id: Number) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .wrapContentSize(Alignment.Center)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text(
            text = "Ejecución avanzada",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
            onClick = {
                navController.navigate("player/$id/simple") {
                    popUpTo(PlayerScreen.Advanced.route) {
                        inclusive = true
                    }
                }
            }) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
                Text("Modo simple")
            }

        }
    }
}