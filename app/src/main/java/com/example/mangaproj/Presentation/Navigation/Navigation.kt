package com.example.mangaproj.Presentation.Navigation

import MainScreen
import MangaDetailScreen
import MangaViewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mangaproj.Presentation.Screens.SignInScreen.SignInScreen
import com.example.mangaproj.Presentation.Screens.SignUpScreen.SignUpScreen
import com.example.mangaproj.Presentation.Screens.SplashScreen.SplashScreen
import com.example.mangaproj.Presentation.ViewModels.SignInViewModel


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mangaViewModel: MangaViewModel = viewModel()
    val mangaList by mangaViewModel.mangaList.collectAsState()

    LaunchedEffect(Unit) {
        mangaViewModel.loadManga() // Загружаем мангу
    }

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
            val authViewModel: SignInViewModel = viewModel()
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                if (authViewModel.getToken(context) == null) {
                    navController.navigate(NavigationRoutes.SIGNIN) {
                        popUpTo(0)
                    }
                }
            }

            if (authViewModel.getToken(context) != null) {
                MainScreen(navController, mangaList) // Передаем список манги в MainScreen
            } else {
                // Показываем загрузку или ничего
            }
        }

        composable("mangaDetail/{mangaId}") { backStackEntry ->
            val mangaId = backStackEntry.arguments?.getString("mangaId")
            val manga = mangaList.firstOrNull { it.id == mangaId }
            if (manga != null) {
                MangaDetailScreen(manga = manga, navController = navController)
            }
        }

    }
}
