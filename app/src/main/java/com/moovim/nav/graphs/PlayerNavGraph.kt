package com.moovim.nav.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.moovim.screens.details.ExerciseDetailsScreen
import com.moovim.screens.details.RoutineDetailsScreen
import com.moovim.screens.player.AdvancedPlayer
import com.moovim.screens.player.SimplePlayer

fun NavGraphBuilder.playerNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.PLAYER,
        startDestination = PlayerScreen.Advanced.route
    ) {
        composable(
            route = PlayerScreen.Simple.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id");
            requireNotNull(id)
            SimplePlayer(navController, id)
        }
        composable(
            route = PlayerScreen.Advanced.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id");
            requireNotNull(id)
            AdvancedPlayer(navController, id)
        }
    }
}
sealed class PlayerScreen(val route: String, var title: String) {
    object Simple : PlayerScreen(route = "player/{id}/simple", title = "Vista Simple")
    object Advanced : PlayerScreen(route = "player/{id}/advanced", title = "Vista avanzada")
}