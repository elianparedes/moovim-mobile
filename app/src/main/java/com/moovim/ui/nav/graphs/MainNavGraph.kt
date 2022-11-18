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
import com.moovim.ui.screens.main.profile.ProfileScreen

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

        searchNavGraph(scaffoldState, navController)

        composable(NavigationItem.Routines.route) {
            RoutinesScreen(scaffoldState = scaffoldState,
                navController =navController,
                onProfileClick = {navController.navigate(NavigationItem.Profile.route)})
        }
        detailsNavGraph(navController)
        playerNavGraph(navController = navController)
        authNavGraph(navController)
        composable(route = NavigationItem.Profile.route){
            ProfileScreen(scaffoldState, navController,
            onLogoutClick = {
                navController.navigate(Graph.AUTHENTICATION) {popUpTo(Graph.HOME) { inclusive = true }}
            })
        }
    }

}

sealed class MainScreen(val route: String, var title: String) {
    object Profile : MainScreen(route = "profile", "Perfil")
}

