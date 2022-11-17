package com.moovim.ui.nav.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moovim.ui.nav.NavigationItem
import com.moovim.ui.screens.main.HomeScreen
import com.moovim.ui.screens.main.routines.RoutinesScreen
import com.moovim.ui.screens.main.SearchScreen

@Composable
fun MainNavGraph(scaffoldState: ScaffoldState, navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.Home.route,
        route = Graph.HOME
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen(navController, paddingValues)
        }
        composable(NavigationItem.Search.route) {
            SearchScreen(navController)
        }
        composable(NavigationItem.Routines.route) {
            RoutinesScreen(scaffoldState, navController)
        }
        detailsNavGraph(navController)
        playerNavGraph(navController = navController)
    }

}

