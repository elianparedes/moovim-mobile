package com.moovim.nav.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moovim.Screen
import com.moovim.nav.NavigationItem
import com.moovim.screens.main.HomeScreen
import com.moovim.screens.main.RoutinesScreen
import com.moovim.screens.main.SearchScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route,
        route = Graph.HOME
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen(navController)
        }
        composable(NavigationItem.Search.route) {
            SearchScreen(navController)
        }
        composable(NavigationItem.Routines.route) {
            RoutinesScreen(navController)
        }
        detailsNavGraph(navController = navController)
        playerNavGraph(navController = navController)
    }

}

