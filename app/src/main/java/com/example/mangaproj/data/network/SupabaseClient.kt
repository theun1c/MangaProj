package com.example.mangaproj.data.network

import android.content.Context
import com.example.mangaproj.presentation.AuthResponse
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SupabaseClient(
    private val context : Context
) {
    private val supabase = createSupabaseClient(
        supabaseUrl = "https://wycxrcsqsnkvyuymrdob.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind5Y3hyY3Nxc25rdnl1eW1yZG9iIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE2NDI2OTgsImV4cCI6MjA1NzIxODY5OH0.ILrvhUWvbkLyEowUuzCFSiKAir-SDPO7QhMutVWbl0E"
    ) {
        install(Postgrest)
    }

    fun signUpWithEmail(emailValue: String, passwordValue: String): Flow<AuthResponse> = flow {
        try{
            supabase.auth.signUpWith(Email){
                email = emailValue
                password = passwordValue
            }
            emit(AuthResponse.Success)
        } catch (ex: Exception) {
            emit(AuthResponse.Error(ex.localizedMessage))
        }
    }

    fun signInWithEmail(emailValue: String, passwordValue: String): Flow<AuthResponse> = flow {
        try{
            supabase.auth.signInWith(Email){
                email = emailValue
                password = passwordValue
            }
            emit(AuthResponse.Success)
        } catch (ex: Exception) {
            emit(AuthResponse.Error(ex.localizedMessage))
        }
    }
}