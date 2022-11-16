package com.moovim.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
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
import com.moovim.ui.theme.NoRippleTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
        floatingActionButton = { FloatingActionButton(navController = navController) },
        backgroundColor = MaterialTheme.colors.background,
        isFloatingActionButtonDocked = true
    ) { paddingValues ->
        Box {
            MainNavGraph(navController = navController, paddingValues = paddingValues)
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
        FloatingActionButton(
            onClick = { navController.navigate("player/1/simple") },
            backgroundColor = MaterialTheme.colors.primary
        ) {
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

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        AnimatedVisibility(
            visible = bottomBarDestination,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            BottomNavigation(
                backgroundColor = Color.Transparent,
                contentColor = Color.White,
                elevation = 0.dp,
                modifier = Modifier
                    .drawWithContent {
                        val colors = listOf(Color.Transparent, Color(0xFF0F0F0F), Color(0xFF0F0F0F))
                        drawRect(
                            brush = Brush.verticalGradient(colors),
                        )
                        drawContent()
                    }
                    .padding(top = 48.dp)) {
                val backStackEntry by navController.currentBackStackEntryAsState();
                val currentRoute = backStackEntry?.destination?.route
                items.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(id = item.icon),
                                contentDescription = item.title,
                                modifier = Modifier.size(32.dp)
                            )
                        },
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


}