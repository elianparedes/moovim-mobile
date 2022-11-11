package com.moovim.ui.screens.details

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.moovim.Screen

@Composable
fun ExerciseDetailsScreen(navController: NavHostController, id: Number) {
    Screen("Detalles de ejercicio $id")
}