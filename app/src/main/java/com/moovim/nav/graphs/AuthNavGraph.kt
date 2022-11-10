package com.moovim.nav.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.moovim.Screen
import com.moovim.screens.auth.LoginScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(onClick = {
                navController.popBackStack()
                navController.navigate(Graph.HOME)
            },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onForgotClick = {
                    navController.navigate(AuthScreen.PasswordRecovery.route)
                })
        }
        composable(route = AuthScreen.SignUp.route) {
            Screen(item = AuthScreen.SignUp.title)
        }
        composable(route = AuthScreen.PasswordRecovery.route) {
            Screen(item = AuthScreen.PasswordRecovery.title)
        }
    }
}

sealed class AuthScreen(val route: String, var title: String) {
    object Login : AuthScreen(route = "login", "Iniciar Sesión")
    object SignUp : AuthScreen(route = "signUp", "Registrarse")
    object PasswordRecovery : AuthScreen(route = "passwordRecovery", "Recuperar contraseña")
}