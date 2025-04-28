package com.example.mangaproj.Presentation.ViewModels

import android.content.Context
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

class SignUpViewModel: ViewModel() {

    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    fun signUp(context: Context, userEmail: String, userPassword: String, userFirstName: String, userLastName: String) {
        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Successful registration!")

                val userId = SupabaseClient.client.auth.currentUserOrNull()?.id
                    ?: throw Exception("User ID not found after registration")

                val user = UserProfile(
                    id = userId,
                    email = userEmail,
                    first_name = userFirstName,
                    last_name = userLastName
                )
                SupabaseClient.client.from("profiles").insert(user)

            } catch (e: Exception) {
                _userState.value = UserState.Error("Error during registration: ${e.message}")
            }
        }
    }

    private fun saveToken(context: Context) {
        viewModelScope.launch {
            val accessToken = SupabaseClient.client.auth.currentAccessTokenOrNull() ?: ""
            PreferenceHelper(context).saveStringData("accessToken", accessToken)
        }
    }

    fun resetState() {
        _userState.value = UserState.Loading
    }
}
