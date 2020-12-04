package qu.cmps312.lingosnacks.repositories

import android.content.Context
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import qu.cmps312.lingosnacks.model.User

class AuthRepository(val context: Context) {

    /* ToDo: Implement userRepository.signIn using FirebaseAuth (email and password authentication). (Done)
       ToDo: After successful sign in call userRepository.getUser to get the user details from Firstore.users collection (Done)
       ToDo: Then Call userRepository.addCurrentUser to add successfully authenticated user to lingosnacks local database (Done)
    */

    private val userCollectionRef by lazy {
        Firebase.firestore.collection("users")
    }

    fun signIn(email: String, password: String) =
        Firebase.auth.signInWithEmailAndPassword(email, password)

    // ToDo: UserRepository.addUser : Add the user to FirebaseAuth & Firestore users collection (password should NOT stored in users collection) (Done)
    fun signUp(user: User) = Firebase.auth.createUserWithEmailAndPassword(user.email, user.password)
        .addOnSuccessListener {
            user.uid = Firebase.auth.currentUser?.uid.toString()
            userCollectionRef.document(Firebase.auth.currentUser?.uid.toString()).set(user)
        }

    // ToDo: Get the authenticated user from lingosnacks local database. Return null if not found (which mean the user did not login before).(Done)
    suspend fun getCurrentUser() = Firebase.auth.currentUser?.uid?.let {
        userCollectionRef.document(it).get().await().toObject(User::class.java)
    }

    // ToDo: signout from FirebaseAuth and delete the user from lingosnacks local database (Done)
    fun signOut() = Firebase.auth.signOut()

    // ToDo: Add successfully authenticated user to lingosnacks local database // for what this needed???
    private fun addCurrentUser(user: User) {}

    // ToDo: Delete authenticated user from lingosnacks local database // for what this needed???
    private fun deleteCurrentUser(user: User) {}
}