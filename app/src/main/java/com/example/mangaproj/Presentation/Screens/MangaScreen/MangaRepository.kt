
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID


class MangaRepository(private val supabaseClient: SupabaseClient) {
    // ... существующие методы ...

    suspend fun getFavorites(userId: String): List<Favorite> {
        return withContext(Dispatchers.IO) {
            try {
                supabaseClient
                    .from("favorites")
                    .select {
                        filter {
                            eq("user_id", userId)
                        }
                    }
                    .decodeList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    suspend fun addFavorite(userId: String, mangaId: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                supabaseClient
                    .from("favorites")
                    .insert(Favorite(
                        id = UUID.randomUUID().toString(),  // Генерируем ID
                        userId = userId,
                        mangaId = mangaId
                    ))
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun removeFavorite(userId: String, mangaId: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                supabaseClient
                    .from("favorites")
                    .delete {
                        filter {
                            eq("user_id", userId)
                            eq("manga_id", mangaId)
                        }
                    }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun getFavoriteManga(userId: String): List<Manga> {
        return withContext(Dispatchers.IO) {
            try {
                // 1. Получаем список ID избранных манг (упрощенный вариант)
                val favoriteIds = supabaseClient
                    .from("favorites")
                    .select(columns = Columns.list("manga_id")) {  // Явно указываем нужные колонки в строке
                        filter { eq("user_id", userId) }
                    }
                    .decodeList<Favorite>()
                    .map { it.mangaId }

                if (favoriteIds.isEmpty()) return@withContext emptyList()

                // 2. Получаем полные данные манг
                supabaseClient
                    .from("manga")
                    .select() {
                        filter {
                            // Создаем OR-условие для каждого ID
                            favoriteIds.forEach { id ->
                                or { eq("id", id) }
                            }
                        }
                    }
                    .decodeList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
    suspend fun getMangaList(): List<Manga> {
        return withContext(Dispatchers.IO) {
            try {
                val response = supabaseClient
                    .from("manga")
                    .select()
                    .decodeList<Manga>() // <-- автоматическое преобразование в список Manga
                response
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }





}