package com.moovim.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.moovim.R
import com.moovim.ui.nav.NavigationItem
import com.moovim.ui.nav.graphs.MainNavGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { FloatingActionButton(navController = navController) },
        backgroundColor = Color.Black,
    ) {
        paddingValues -> Column(Modifier.padding(paddingValues)) {
        MainNavGraph(navController = navController)
    }

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun FloatingActionButton(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Home,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val fabDestination = items.any { it.route == currentDestination?.route }

    AnimatedVisibility(
        visible = fabDestination,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        FloatingActionButton(onClick = {navController.navigate("player/1/simple")}) {
            Icon(
                painterResource(R.drawable.ic_play),
                contentDescription = "play_exercise",
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
        }
    }

}

@Composable
private fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Search,
        NavigationItem.Routines
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = items.any { it.route == currentDestination?.route }

    AnimatedVisibility(
        visible = bottomBarDestination,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        BottomNavigation(backgroundColor = Color.Black, contentColor = Color.White) {
            val backStackEntry by navController.currentBackStackEntryAsState();
            val currentRoute = backStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    alwaysShowLabel = false,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

            }
        }
    }


}