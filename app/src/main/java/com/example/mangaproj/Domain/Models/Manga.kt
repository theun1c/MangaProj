import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Manga(
    val id: String,
    val title: String,
    val description: String,
    @SerialName("image_url")
    val imageUrl: String
)
