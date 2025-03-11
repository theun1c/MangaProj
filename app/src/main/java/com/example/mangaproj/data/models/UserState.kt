package com.example.mangaproj.data.models

import co.touchlab.kermit.Message


sealed class UserState {
    object Loading: UserState()
    data class Success(val message: String): UserState()
    data class Error(val message: String): UserState()
}