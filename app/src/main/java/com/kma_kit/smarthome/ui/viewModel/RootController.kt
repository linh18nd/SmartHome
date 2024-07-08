import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.kma_kit.smarthome.data.entity.DeviceEntity
import com.kma_kit.smarthome.data.entity.parseJson
import com.kma_kit.smarthome.data.model.request.UpdateUser
import com.kma_kit.smarthome.data.model.response.Device
import com.kma_kit.smarthome.data.model.response.HomeResponse
import com.kma_kit.smarthome.data.model.response.UserResponse
import com.kma_kit.smarthome.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class RootController : ViewModel() {
    private val _userInfo = MutableLiveData<UserResponse>()
    val userInfo: LiveData<UserResponse> get() = _userInfo

    private val _devices = MutableLiveData<List<DeviceEntity>>()

    val devices: LiveData<List<DeviceEntity>> get() = _devices

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

    fun updateDevices(data: String) {
        Log.d("RootController", "updateDevices called with data: $data")
        try {
            val devices = parseData(data)
            _devices.postValue(devices)
            devices.forEach { device ->
                if (device.type == "humidity") {
                    Log.d("RootController", "Device updated: $device")
                }
            }
            Log.d("RootController", "Devices updated successfully")
        } catch (e: Exception) {
            Log.e("RootController", "Failed to update devices", e)
        }
    }
    private fun parseData(data: String): List<DeviceEntity> {
        Log.d("RootController", "Parsing data: $data")
        val gson = Gson()
        val deviceListType = object : TypeToken<List<DeviceEntity>>() {}.type
        val devices: List<DeviceEntity> = gson.fromJson(data, deviceListType)
        return devices.map { device ->
            DeviceEntity(device.device_id, device.type, device.value)
        }
    }
}
