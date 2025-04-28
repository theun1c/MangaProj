
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MangaRepository(private val supabaseClient: SupabaseClient) {
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
