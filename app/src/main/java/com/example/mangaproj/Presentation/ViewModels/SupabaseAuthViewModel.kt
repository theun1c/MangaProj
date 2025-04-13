package com.example.mangaproj.Presentation.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaproj.Domain.Models.UserState
import com.example.mangaproj.Data.Network.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class SupabaseAuthViewModel: ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    fun signUp(
        userEmail: String,
        userPassword: String,
    ) {
        // Валидация email
        if (userEmail.isBlank()) {
            _userState.value = UserState.Error("Email cannot be empty")
            return
        }
        if (!isValidEmail(userEmail)) {
            _userState.value = UserState.Error("Please enter a valid email")
            return
        }

        // Валидация пароля
        if (userPassword.isBlank()) {
            _userState.value = UserState.Error("Password cannot be empty")
            return
        }
        if (userPassword.length < 6) {  // Минимальная длина пароля
            _userState.value = UserState.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                _userState.value = UserState.Success("Successful registration!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }

    fun signIn(
        userEmail: String,
        userPassword: String,
    ) {
        // Простая валидация email
        if (!isValidEmail(userEmail)) {
            _userState.value = UserState.Error("Please enter a valid email address")
            return
        }
        // Валидация пароля
        if (userPassword.isBlank()) {
            _userState.value = UserState.Error("Password cannot be empty")
            return
        }
        if (userPassword.length < 6) {  // Минимальная длина пароля
            _userState.value = UserState.Error("Password must be at least 6 characters")
            return
        }
        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                _userState.value = UserState.Success("Successful log in!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }

    // Функция для проверки валидности email
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}