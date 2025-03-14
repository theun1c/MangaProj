package com.example.mangaproj.presentation.screens.signinscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mangaproj.presentation.navigation.Navigation
import com.example.mangaproj.presentation.components.BasicBlueButton
import com.example.mangaproj.presentation.components.SimpleTextField
import com.example.mangaproj.presentation.navigation.NavigationRoutes


@Composable
fun SignInScreen(navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding( start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "ВХОД",
                fontSize = 32.sp,
                color = Color.Blue,
                fontWeight = FontWeight.W800,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SimpleTextField("Введите логин")
            SimpleTextField("Введите пароль")
            BasicBlueButton("Войти")
            Row (
                modifier = Modifier
                    .padding(16.dp)
            ){
                Text(
                    "Нет аккаунта? ",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.W600,
                )
                Text(
                    "Создать аккаунт",
                    fontSize = 14.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.clickable {
                        navController.navigate(NavigationRoutes.SIGNUP)
                    }
                )
            }
            
        }
    }
}
