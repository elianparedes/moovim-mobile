package com.moovim.ui.nav.graphs


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.moovim.ui.screens.auth.LoginViewModel
import com.moovim.ui.screens.main.AuxCategoriesScreen

import com.moovim.ui.screens.main.AuxSearchScreen
import com.moovim.ui.screens.main.search.SearchViewModel

fun NavGraphBuilder.searchNavGraph(scaffoldState: ScaffoldState, navController: NavHostController, paddingValues: PaddingValues){

    navigation(
        route = Graph.SEARCH,
        startDestination = "categories"
    ){
        composable(route = "categories"){navBackStackEntry ->
            val parentEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry("categories")
            }
            val searchViewModel = hiltViewModel<SearchViewModel>(parentEntry)
            AuxCategoriesScreen(scaffoldState, navController,searchViewModel, paddingValues)
        }
        composable(route = "search"){navBackStackEntry ->
            val parentEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry("categories")
            }
            val searchViewModel = hiltViewModel<SearchViewModel>(parentEntry)
            AuxSearchScreen(scaffoldState, navController, searchViewModel, paddingValues)
        }
    }
}