package com.example.mangaproj.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mangaproj.data.network.SupabaseClient
import com.example.mangaproj.presentation.screens.signinscreen.SignInScreen
import com.example.mangaproj.presentation.screens.signupscreen.SignUpScreen
import com.example.mangaproj.presentation.screens.splashscreen.SplashScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val supabaseClient = remember { SupabaseClient(context) } // Создайте экземпляр SupabaseClient

    NavHost(navController = navController, startDestination = NavigationRoutes.SPLASH) {
        composable(NavigationRoutes.SPLASH) {
            SplashScreen(navController)
        }
        composable(NavigationRoutes.SIGNIN) {
            SignInScreen(navController, supabaseClient) // Передайте supabaseClient
        }
        composable(NavigationRoutes.SIGNUP) {
            SignUpScreen(navController, supabaseClient) // Передайте supabaseClient
        }
    }
}
