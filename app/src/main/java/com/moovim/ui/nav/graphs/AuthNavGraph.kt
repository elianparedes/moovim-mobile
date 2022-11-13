package com.moovim.ui.nav.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.moovim.Screen
import com.moovim.ui.screens.auth.*

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(onClick = {
                navController.navigate(AuthScreen.LoginName.route)
            },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onForgotClick = {
                    navController.navigate(AuthScreen.PasswordRecovery.route)
                })
        }
        composable(route = AuthScreen.SignUp.route) {
            SignUpScreen(
                onContinueClick = {
                    navController.navigate(AuthScreen.SignUpPassword.route)
                }
            )
        }
        composable(route = AuthScreen.PasswordRecovery.route) {
            Screen(item = AuthScreen.PasswordRecovery.title)
        }
        composable (route = AuthScreen.SignUpPassword.route){
            SignUpPasswordScreen()
        }
        composable(route = AuthScreen.LoginName.route){
            LoginNameScreen(onContinueClick = {
                navController.navigate(AuthScreen.LoginPassword.route)
            })
        }
        composable(route = AuthScreen.LoginPassword.route){
            LoginPasswordScreen(
                onContinueClick = {
                    navController.navigate(Graph.HOME)
                }
            )
        }
    }
}

sealed class AuthScreen(val route: String, var title: String) {
    object Login : AuthScreen(route = "login", "Iniciar Sesión")
    object LoginName: AuthScreen(route = "loginName", "Ingresar nombre sesión")
    object LoginPassword: AuthScreen(route = "loginPassword", "Ingresar contraseña sesión")
    object SignUp : AuthScreen(route = "signUp", "Registrarse")
    object SignUpPassword : AuthScreen(route = "signUpPassword", "Ingresar contraseña")
    object PasswordRecovery : AuthScreen(route = "passwordRecovery", "Recuperar contraseña")
}