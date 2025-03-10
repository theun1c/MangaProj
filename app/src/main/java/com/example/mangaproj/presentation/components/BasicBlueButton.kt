package com.example.mangaproj.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BasicBlueButton(buttonText: String){
    Button(
        onClick = { /* Действие при нажатии */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,

        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(top = 16.dp)
    ) {
        Text(text = buttonText)
    }
}
