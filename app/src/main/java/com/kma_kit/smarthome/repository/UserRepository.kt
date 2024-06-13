package com.kma_kit.smarthome.repository

import ApiClient
import com.kma_kit.smarthome.data.model.request.UserAuth
import com.kma_kit.smarthome.data.model.request.UserRegistration
import com.kma_kit.smarthome.data.model.response.AuthResponse
import com.kma_kit.smarthome.data.model.response.UserResponse

class UserRepository {
    suspend fun registerUser(userRegistration: UserRegistration): UserResponse {
        return ApiClient.api.registerUser(userRegistration)
    }

    suspend fun loginUser(userAuth: UserAuth): AuthResponse {
        return ApiClient.api.loginUser(userAuth)
    }

}