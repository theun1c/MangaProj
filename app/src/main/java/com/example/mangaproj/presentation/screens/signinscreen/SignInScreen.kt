package com.example.mangaproj.presentation.screens.signinscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mangaproj.presentation.navigation.Navigation
import com.example.mangaproj.presentation.components.BasicBlueButton
import com.example.mangaproj.presentation.components.SimpleTextField


@Composable
fun SignInScreen(navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding( start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Центрирование по горизонтали внутри Column
        ) {
            SimpleTextField()
            SimpleTextField()
            BasicBlueButton()
        }
    }
}
