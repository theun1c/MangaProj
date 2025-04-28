// MangaViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangaproj.Data.Network.SupabaseClient
import com.example.mangaproj.Domain.Models.Manga
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MangaViewModel : ViewModel() {
    private val _mangaList = MutableStateFlow<List<Manga>>(emptyList())
    val mangaList: StateFlow<List<Manga>> = _mangaList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadManga() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result =  SupabaseClient.client
                    .from("manga")
                    .select()
                    .decodeList<Manga>()
                _mangaList.value = result
            } catch (e: Exception) {
                // Обработка ошибок
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}