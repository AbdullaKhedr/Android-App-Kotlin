package qu.cmps312.lingosnacks.model

import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    @DocumentId
    val ratingId: String = "",
    val packageId: String = "",
    val comment: String = "",
    val doneOn: String = "",
    val doneBy: String = "",
    val rating: Double = 0.0
)