package com.example.mangaproj.Presentation.Screens.SignInScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mangaproj.Domain.Models.UserState
import com.example.mangaproj.Presentation.Components.BasicBlueButton
import com.example.mangaproj.Presentation.Components.EmailTextField
import com.example.mangaproj.Presentation.Components.PasswordTextField
import com.example.mangaproj.Presentation.Navigation.NavigationRoutes
import com.example.mangaproj.Presentation.ViewModels.SupabaseAuthViewModel


@Composable
fun SignInScreen(navController: NavHostController, signInViewModel: SupabaseAuthViewModel = viewModel()) {
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var confirmPasswordValue by remember { mutableStateOf("") }
    val userState by signInViewModel.userState
    var currentUserState by remember {
        mutableStateOf("")
    }
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
                color = Color(0xFF24b9bd),
                fontWeight = FontWeight.W800,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            EmailTextField(
                textFieldText = "Введите логин",
                emailValue = emailValue,
                onValueChange = { emailValue = it }
            )

            PasswordTextField(
                textFieldText = "Введите пароль",
                passwordValue = passwordValue,
                onValueChange = { passwordValue = it }
            )

            BasicBlueButton(buttonText = "Войти", onClick = {
                    signInViewModel.signIn(emailValue, passwordValue)
                }
            )

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
                    color = Color(0xFF24b9bd),
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.clickable {
                        navController.navigate(NavigationRoutes.SIGNUP)
                    }
                )
            }
            when(userState){
                is UserState.Loading -> {

                }
                is UserState.Success -> {
                    val message = (userState as UserState.Success).message
                    currentUserState = message
                    navController.navigate(NavigationRoutes.MAIN)
                }
                is UserState.Error -> {
                    val message = (userState as UserState.Error).message
                    currentUserState = message
                }
            }
            if(currentUserState.isNotEmpty()){
                Text(text = currentUserState)
            }
        }
    }
}
