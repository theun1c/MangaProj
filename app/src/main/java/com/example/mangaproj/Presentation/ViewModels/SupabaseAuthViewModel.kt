package com.example.mangaproj.Presentation.ViewModels

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaproj.Domain.Models.UserState
import com.example.mangaproj.Data.Network.SupabaseClient
import com.example.mangaproj.Data.Utils.PreferenceHelper
import com.example.mangaproj.Domain.Models.UserProfile
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class SupabaseAuthViewModel: ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    fun signUp(
        context: Context,
        userEmail: String,
        userPassword: String,
        userFirstName: String,
        userLastName: String,
    ) {
        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Successful registration!")
                val userId = SupabaseClient.client.auth.currentUserOrNull()?.id
                    ?: throw Exception("Не удалось получить ID пользователя")
                val user = UserProfile(id = userId , email = userEmail, last_name = userLastName, first_name = userFirstName)
                SupabaseClient.client.from("profiles").insert(user)
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error during registration: ${e.message}")
            }
        }
    }

    private fun saveToken(context: Context){
        viewModelScope.launch {
            val accessToken = SupabaseClient.client.auth.currentAccessTokenOrNull() ?: ""
            val pref = PreferenceHelper(context)
            pref.saveStringData("accessToken", accessToken)
        }
    }

    fun getToken(context: Context): String? {
        val pref = PreferenceHelper(context)
        return pref.getStringData("accessToken")
    }

    fun signIn(
        context: Context,
        userEmail: String,
        userPassword: String,
    ) {
        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Successful log in!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error during logging in: ${e.message}")
            }
        }
    }

    fun signOut(context: Context) {
        viewModelScope.launch {
            try {
                // Пытаемся выйти через Supabase
                runCatching {
                    SupabaseClient.client.auth.signOut()
                }.onFailure { e ->
                    // Игнорируем ошибку "session_not_found", так как цель - выход
                    if (e.message?.contains("session_not_found") != true) {
                        throw e
                    }
                }

                // Очищаем локальные данные в любом случае
                PreferenceHelper(context).removeKey("accessToken")
                _userState.value = UserState.Success("Logged out successfully")

            } catch (e: Exception) {
                _userState.value = UserState.Error("Logout completed locally: ${e.message}")
            }
        }
    }

    fun isUserLoggedIn(context: Context) {
        viewModelScope.launch {
            try {
                val token = getToken(context)
                if (token.isNullOrEmpty()) {
                    _userState.value = UserState.Error("User is not logged in!")
                    return@launch
                }

                // Проверяем сессию более безопасным способом
                runCatching {
                    SupabaseClient.client.auth.retrieveUser(token)
                }.onSuccess {
                    _userState.value = UserState.Success("User is logged in!")
                }.onFailure {
                    // Если сессия недействительна, очищаем токен
                    PreferenceHelper(context).removeKey("accessToken")
                    _userState.value = UserState.Error("Session expired, please log in again")
                }

            } catch (e: Exception) {
                _userState.value = UserState.Error("Error checking login status: ${e.message}")
            }
        }
    }

    fun resetState() {
        _userState.value = UserState.Loading
    }

}