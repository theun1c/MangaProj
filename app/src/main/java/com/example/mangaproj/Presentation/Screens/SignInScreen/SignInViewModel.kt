package com.example.mangaproj.Presentation.ViewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaproj.Domain.Models.UserState
import com.example.mangaproj.Data.Network.SupabaseClient
import com.example.mangaproj.Data.Utils.PreferenceHelper
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {

    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    fun signIn(context: Context, userEmail: String, userPassword: String) {
        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signInWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Successful login!")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error during login: ${e.message}")
            }
        }
    }

    private fun saveToken(context: Context) {
        viewModelScope.launch {
            val accessToken = SupabaseClient.client.auth.currentAccessTokenOrNull() ?: ""
            PreferenceHelper(context).saveStringData("accessToken", accessToken)
        }
    }

    fun isUserLoggedIn(context: Context) {
        viewModelScope.launch {
            try {
                val token = PreferenceHelper(context).getStringData("accessToken")
                if (token.isNullOrEmpty()) {
                    _userState.value = UserState.Error("User is not logged in!")
                    return@launch
                }

                runCatching {
                    SupabaseClient.client.auth.retrieveUser(token)
                }.onSuccess {
                    _userState.value = UserState.Success("User is logged in!")
                }.onFailure {
                    PreferenceHelper(context).removeKey("accessToken")
                    _userState.value = UserState.Error("Session expired")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("Login check failed: ${e.message}")
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

    fun getToken(context: Context): String? {
        val pref = PreferenceHelper(context)
        return pref.getStringData("accessToken")
    }
    fun resetState() {
        _userState.value = UserState.Loading
    }
}
