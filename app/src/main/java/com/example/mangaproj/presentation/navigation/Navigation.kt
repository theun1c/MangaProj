package com.example.mangaproj.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mangaproj.presentation.screens.signinscreen.SignInScreen
import com.example.mangaproj.presentation.screens.signupscreen.SignUpScreen
import com.example.mangaproj.presentation.screens.splashscreen.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoutes.SPLASH) {
        composable(NavigationRoutes.SPLASH) {
            SplashScreen(navController)
        }
        composable(NavigationRoutes.SIGNIN) {
            SignInScreen(navController)
        }
        composable(NavigationRoutes.SIGNUP) {
            SignUpScreen(navController)
        }
    }
}
