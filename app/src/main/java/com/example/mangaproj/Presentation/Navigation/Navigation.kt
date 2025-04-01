package com.example.mangaproj.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mangaproj.Presentation.Screens.MainScreen.MainScreen
import com.example.mangaproj.Presentation.Screens.SignInScreen.SignInScreen
import com.example.mangaproj.Presentation.Screens.SignUpScreen.SignUpScreen
import com.example.mangaproj.Presentation.Screens.SplashScreen.SplashScreen


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
        composable(NavigationRoutes.MAIN) {
            MainScreen(navController)
        }
    }
}
