package com.example.mangaproj.Presentation.Screens.MainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mangaproj.Domain.Models.UserState
import com.example.mangaproj.Presentation.Components.BasicBlueButton
import com.example.mangaproj.Presentation.Components.LoadingComponent
import com.example.mangaproj.Presentation.Navigation.NavigationRoutes
import com.example.mangaproj.Presentation.ViewModels.SupabaseAuthViewModel

@Composable
fun MainScreen(navController: NavHostController, mainViewModel : SupabaseAuthViewModel = viewModel()) {
    val userState by mainViewModel.userState
    var currentUserState by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    // Проверяем авторизацию при входе
    LaunchedEffect(Unit) {
        mainViewModel.isUserLoggedIn(context)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "MAIN SCREEN!!!")
            BasicBlueButton(buttonText = "Выход",
                onClick = {
                    mainViewModel.signOut(context)
                    navController.navigate(NavigationRoutes.SIGNIN) {
                        // Очищаем стек навигации
                        popUpTo(0)
                    }

                }
            )
        }
    }

//    when(userState){
//        is UserState.Loading -> {
//            LoadingComponent()
//        }
//        is UserState.Success -> {
//            val message = (userState as UserState.Success).message
//            currentUserState = message
//            navController.navigate(NavigationRoutes.SIGNIN)
//
//        }
//        is UserState.Error -> {
//            val message = (userState as UserState.Error).message
//            currentUserState = message
//        }
//    }
//
//    if(currentUserState.isNotEmpty()){
//        Text(text = currentUserState)
//    }
}
