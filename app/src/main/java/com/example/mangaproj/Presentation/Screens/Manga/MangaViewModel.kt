import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.mangaproj.Data.Network.SupabaseClient // Импортируйте ваш объект

class MangaViewModel : ViewModel() {

    // Создаем репозиторий, передавая в него клиента из SupabaseClient
    private val repository = MangaRepository(SupabaseClient.client)

    private val _mangaList = MutableStateFlow<List<Manga>>(emptyList())
    val mangaList: StateFlow<List<Manga>> = _mangaList

    fun loadManga() {
        viewModelScope.launch {
            val manga = repository.getMangaList()
            _mangaList.value = manga
        }
    }
}
