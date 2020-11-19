package qu.cmps312.countryvisit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Continent(
    @PrimaryKey
    var name: String
) {
    override fun toString(): String {
        return name
    }
}