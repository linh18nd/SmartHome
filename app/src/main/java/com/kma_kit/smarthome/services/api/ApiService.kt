package com.kma_kit.smarthome.services.api

import com.kma_kit.smarthome.data.model.request.UserRegistration
import com.kma_kit.smarthome.data.model.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body userRegistration: UserRegistration): UserResponse

}