package qu.cmps312.lingosnacks.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import qu.cmps312.lingosnacks.model.User
import qu.cmps312.lingosnacks.repositories.AuthRepository

data class UserInfo(val uid: String = "", val email: String = "", val role: String = "")

class AuthViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val authRepository = AuthRepository(appContext)

    // ToDo: Initialize _currentUser by calling userRepository.getCurrentUser to get cached authenticated user (Done?)
    private val _currentUser = MutableLiveData<User?>(null)
    val currentUser = _currentUser as LiveData<User?>

    init {
        viewModelScope.launch {
            _currentUser.value = authRepository.getCurrentUser()
        }
    }

    fun signIn(email: String, password: String) = authRepository.signIn(email, password)

    fun signUp(user: User) = authRepository.signUp(user)

    fun setCurrentUser() = viewModelScope.launch {
        _currentUser.value = authRepository.getCurrentUser()
    }

    fun signOut() {
        authRepository.signOut()
        _currentUser.value = null
    }

    fun getCurrentUserInfo(): UserInfo {
        var userInfo = UserInfo()
        currentUser.value?.let {
            userInfo = UserInfo(
                currentUser.value!!.uid,
                currentUser.value!!.email,
                currentUser.value!!.role
            )
        }
        return userInfo
    }
}