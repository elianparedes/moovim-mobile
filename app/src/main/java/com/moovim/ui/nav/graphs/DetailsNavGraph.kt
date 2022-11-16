package com.moovim.ui.nav.graphs

import android.content.Intent
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
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://moovim.app/{routineId}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(navArgument("routineId") { type = NavType.IntType })
        ) { backStackEntry ->
            val routineId = backStackEntry.arguments?.getInt("routineId");
            requireNotNull(routineId)
            RoutineDetailsScreen(navController, routineId)
        }
        composable(
            route = DetailsScreen.Exercise.route,
            arguments = listOf(navArgument("exerciseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getInt("exerciseId");
            requireNotNull(exerciseId)
            ExerciseDetailsScreen(navController, exerciseId)
        }
    }
}

sealed class DetailsScreen(val route: String, var title: String) {
    object Routine : DetailsScreen(route = "routines/{routineId}", title = "Rutina")
    object Exercise : DetailsScreen(route = "exercises/{exerciseId}", title = "Ejercicio")
}