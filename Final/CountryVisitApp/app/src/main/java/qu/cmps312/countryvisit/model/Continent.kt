package qu.cmps312.countryvisit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

// ToDo: add the entity properties and make it a data class
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