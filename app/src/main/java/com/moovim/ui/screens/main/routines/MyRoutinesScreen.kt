package com.moovim.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moovim.ui.screens.main.routines.MyRoutinesViewModel

@Composable
fun RoutinesScreen(
    navController: NavHostController,
    viewModel: MyRoutinesViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mis rutinas", color = Color.White)

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.userRoutines.forEach { routine ->
                RoutineCard(
                    name = routine.name,
                    onClick = { navController.navigate("routines/${routine.id}") })
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutineCard(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(name)
        }

    }
}