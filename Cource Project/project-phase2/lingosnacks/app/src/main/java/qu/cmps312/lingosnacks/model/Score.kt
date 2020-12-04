package qu.cmps312.lingosnacks.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Score(
    @DocumentId
    val scoreId: String = "",
    val uid: String = "",
    val gameName: String = "",
    val score: Int = 0,
    val outOf: Int = 0,
    val doneOn: String = "",
    @PrimaryKey(autoGenerate = true)
    val localId : Int = 0
)