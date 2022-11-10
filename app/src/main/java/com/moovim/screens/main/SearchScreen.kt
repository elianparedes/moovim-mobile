package com.moovim.screens.main;

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Buscar", color = Color.White)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),

            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(10) { id ->
                Card(
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp),
                    onClick = { navController.navigate("routines/$id") }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Routina $id")
                    }
                }
            }
        }

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