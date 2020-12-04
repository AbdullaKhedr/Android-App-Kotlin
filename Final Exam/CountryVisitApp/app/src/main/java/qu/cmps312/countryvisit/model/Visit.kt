package qu.cmps312.countryvisit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Visit(
    @PrimaryKey
    var code: String = "",
    var name: String = "",
    var rating: Float = 0.toFloat(),
    var amount: Float = 0.toFloat()
)