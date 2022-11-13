package com.moovim.ui.screens.details

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
import androidx.navigation.NavHostController
import com.moovim.ui.screens.details.routine.RoutineDetailsViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoutineDetailsScreen(
    navController: NavHostController,
    routineId: Int,
    viewModel: RoutineDetailsViewModel = hiltViewModel()
) {

    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(state.name, color = Color.White)
        Text(state.detail, color = Color.White)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),

            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(10) { id ->
                Card(
                    modifier = Modifier
                        .height(64.dp)
                        .fillMaxWidth(),
                    onClick = { navController.navigate("exercises/$id") }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Ejercicio $id")
                    }
                }
            }
        }
    }


}