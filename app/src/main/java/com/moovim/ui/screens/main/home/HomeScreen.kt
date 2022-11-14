package com.moovim.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moovim.ui.components.ExerciseRoutineCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavHostController, paddingValues: PaddingValues) {
    val items = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Inicio", color = Color.White)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Rutina actual")
            }
        }
        items.forEach { id ->
            ExerciseRoutineCard(
                title = "Ejercicio $id",
                group = "Grupo $id",
                repetitions = 10,
                duration = 10
            ) {

            }
        }
        Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding() - 16.dp))
    }
}
