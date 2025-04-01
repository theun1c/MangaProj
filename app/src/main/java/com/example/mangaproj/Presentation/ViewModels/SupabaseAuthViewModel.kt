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
    ){
        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signUpWith(Email){
                    email = userEmail
                    password = userPassword
                }
                _userState.value = UserState.Success("Succsessful register new user!")
            } catch (e: Exception){
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }

    fun signIn(
        userEmail: String,
        userPassword: String,
    ){
        viewModelScope.launch {
            try {
                SupabaseClient.client.auth.signInWith(Email){
                    email = userEmail
                    password = userPassword
                }
                _userState.value = UserState.Success("Succsessful log in!")

            } catch (e: Exception){
                _userState.value = UserState.Error("Error: ${e.message}")
            }
        }
    }


}