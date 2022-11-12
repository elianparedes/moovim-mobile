package com.moovim.ui.nav.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.moovim.ui.screens.details.ExerciseDetailsScreen
import com.moovim.ui.screens.details.RoutineDetailsScreen

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Routine.route
    ) {
        composable(
            route = DetailsScreen.Routine.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id");
            requireNotNull(id)
            RoutineDetailsScreen(navController, id)
        }
        composable(
            route = DetailsScreen.Exercise.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id");
            requireNotNull(id)
            ExerciseDetailsScreen(navController, id)
        }
    }
}

sealed class DetailsScreen(val route: String, var title: String) {
    object Routine : DetailsScreen(route = "routines/{id}", title = "Rutina")
    object Exercise : DetailsScreen(route = "exercises/{id}", title = "Ejercicio")
}