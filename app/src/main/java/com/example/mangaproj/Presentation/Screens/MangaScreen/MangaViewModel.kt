import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.mangaproj.Data.Network.SupabaseClient // Импортируйте ваш объект

class MangaViewModel : ViewModel() {
    private val repository = MangaRepository(SupabaseClient.client)
    private val _mangaList = MutableStateFlow<List<Manga>>(emptyList())
    val mangaList: StateFlow<List<Manga>> = _mangaList

    private val _favorites = MutableStateFlow<List<Manga>>(emptyList())
    val favorites: StateFlow<List<Manga>> = _favorites

    // Добавим текущего пользователя
    private var currentUserId: String? = null

    fun setCurrentUser(userId: String) {
        currentUserId = userId
        loadFavorites(userId)
    }

    fun loadManga() {
        viewModelScope.launch {
            val manga = repository.getMangaList()
            // Обновляем состояние isFavorite для каждой манги
            _mangaList.value = manga.map { mangaItem ->
                mangaItem.copy(isFavorite = _favorites.value.any { it.id == mangaItem.id })
            }
        }
    }

    fun loadFavorites(userId: String) {
        viewModelScope.launch {
            val favoriteManga = repository.getFavoriteManga(userId)
            _favorites.value = favoriteManga

            // Обновляем состояние isFavorite в основном списке
            _mangaList.value = _mangaList.value.map { mangaItem ->
                mangaItem.copy(isFavorite = favoriteManga.any { it.id == mangaItem.id })
            }
        }
    }

    fun toggleFavorite(manga: Manga) {
        viewModelScope.launch {
            currentUserId?.let { userId ->
                if (manga.isFavorite) {
                    // Удаляем из избранного
                    if (repository.removeFavorite(userId, manga.id)) {
                        // Обновляем favorites
                        _favorites.value = _favorites.value.filter { it.id != manga.id }
                        // Обновляем mangaList
                        _mangaList.value = _mangaList.value.map {
                            if (it.id == manga.id) it.copy(isFavorite = false) else it
                        }
                    }
                } else {
                    // Добавляем в избранное
                    if (repository.addFavorite(userId, manga.id)) {
                        // Обновляем favorites
                        _favorites.value = _favorites.value + manga.copy(isFavorite = true)
                        // Обновляем mangaList
                        _mangaList.value = _mangaList.value.map {
                            if (it.id == manga.id) it.copy(isFavorite = true) else it
                        }
                    }
                }
            }
        }
    }

    private fun updateMangaListFavoriteStatus(mangaId: String, isFavorite: Boolean) {
        _mangaList.value = _mangaList.value.map { manga ->
            if (manga.id == mangaId) manga.copy(isFavorite = isFavorite) else manga
        }
    }
}

