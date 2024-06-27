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

    fun fetchUserInfo() {
        viewModelScope.launch {
            val fetchedUserInfo = apiCallToFetchUserInfo()
            _userInfo.value = fetchedUserInfo
        }
    }

    fun updateUserInfo(user: UpdateUser) {
        viewModelScope.launch {
            val updatedUserInfo = UserRepository().updateUser(user)
            _userInfo.value = updatedUserInfo.body()
        }
    }

    private suspend fun apiCallToFetchUserInfo(): UserResponse {
        val response = UserRepository().getUser()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Failed to fetch user info")
        }
    }
}