package qu.cmps312.lingosnacks.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.Serializable
import qu.cmps312.lingosnacks.ui.viewmodel.UserInfo

@Serializable
@Entity
data class LearningPackage(
    @PrimaryKey
    @DocumentId // to let Firestore auto-set the packageId
    var packageId: String = "",
    var title: String = "",
    var description: String = "",
    var category: String = "",
    var iconUrl: String = "",
    var language: String = "",
    var level: String = "",
    var author: String = "",
    var keywords: String = "",
    var lastUpdatedDate: String = "",
    var version: Int = 0,
    var avgRating: Double = 0.0,
    var numRatings: Int = 0,
    var words: MutableList<Word> = mutableListOf()
) {
    fun isOwner(userInfo: UserInfo) = userInfo.email.equals(author, true)
    fun isTeacher(userInfo: UserInfo) = userInfo.role.equals("Teacher", true)
}