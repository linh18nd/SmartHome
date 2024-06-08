package com.kma_kit.smarthome.repository

import ApiClient
import com.kma_kit.smarthome.data.model.request.UserRegistration
import com.kma_kit.smarthome.data.model.response.UserResponse

class UserRepository {
    suspend fun registerUser(userRegistration: UserRegistration): UserResponse {
        return ApiClient.api.registerUser(userRegistration)
    }

}