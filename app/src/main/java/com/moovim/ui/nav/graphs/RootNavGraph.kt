package com.moovim.ui.nav.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moovim.ui.screens.main.MainScreen

@Composable
fun RootNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDestination,
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            MainScreen()
        }
        detailsNavGraph(navController)
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
    const val PLAYER = "player_graph"
    const val SEARCH = "search_graph"
}