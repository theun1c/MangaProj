import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Favorite(
    val id: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("manga_id")
    val mangaId: String
)
