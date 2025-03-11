package com.example.mangaproj.data.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://wycxrcsqsnkvyuymrdob.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Ind5Y3hyY3Nxc25rdnl1eW1yZG9iIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDE2NDI2OTgsImV4cCI6MjA1NzIxODY5OH0.ILrvhUWvbkLyEowUuzCFSiKAir-SDPO7QhMutVWbl0E"
    ) {
        install(Postgrest)
    }
}