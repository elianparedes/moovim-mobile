package com.moovim.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.moovim.ui.viewmodels.MyRoutinesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutinesScreen(navController: NavHostController, viewModel: MyRoutinesViewModel = hiltViewModel()) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mis rutinas", color = Color.White)

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.routines.forEach { routine ->
                RoutineCard(name = routine.name)
            }
        }

    }
}

@Composable
fun RoutineCard(name: String){
    Card(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(name)
        }

    }
}