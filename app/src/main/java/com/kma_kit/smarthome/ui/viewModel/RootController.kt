import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kma_kit.smarthome.data.model.request.UpdateUser
import com.kma_kit.smarthome.data.model.response.UserResponse
import com.kma_kit.smarthome.repository.UserRepository
import kotlinx.coroutines.launch

class RootController : ViewModel() {
    private val _userInfo = MutableLiveData<UserResponse>()
    val userInfo: LiveData<UserResponse> get() = _userInfo

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchUserInfo() {
        viewModelScope.launch {
            try {
                val fetchedUserInfo = apiCallToFetchUserInfo()
                _userInfo.value = fetchedUserInfo
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateUserInfo(user: UpdateUser) {
        viewModelScope.launch {
            try {
                val updatedUserInfo = UserRepository().updateUser(user)
                if (updatedUserInfo.isSuccessful) {
                    _userInfo.value = updatedUserInfo.body()
                } else {
                    _error.value = "Failed to update user info"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private suspend fun apiCallToFetchUserInfo(): UserResponse {
        try {
            val response = UserRepository().getUser()
            if (response.isSuccessful) {
                return response.body()!!
            } else {
                throw Exception("Failed to fetch user info")
            }
        } catch (e: Exception) {
            throw Exception("Failed to fetch user info")
        }
    }
}
