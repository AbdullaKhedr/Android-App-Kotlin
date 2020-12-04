package qu.cmps312.lingosnacks.model

import com.google.firebase.firestore.Exclude
import kotlinx.serialization.Serializable

@Serializable
data class User(
    var uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    // Marks property as excluded from saving to Firestore
    @get:Exclude val password: String = "",
    val role: String = "",
    val photoUri: String = ""
) {
    override fun toString() = "${firstName.trim()} ${lastName.trim()} - ${email.trim()}".trim()
}