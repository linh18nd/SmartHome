package com.kma_kit.smarthome.services.api

import com.kma_kit.smarthome.data.model.request.ChangePassword
import com.kma_kit.smarthome.data.model.request.UpdateUser
import com.kma_kit.smarthome.data.model.request.UserAuth
import com.kma_kit.smarthome.data.model.response.AuthResponse
import com.kma_kit.smarthome.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @GET("user/me/")
    suspend fun getUser(): Response<UserResponse>
    @POST("user/auth/")
    suspend fun loginUser(@Body userAuth: UserAuth): Response<AuthResponse>

    @PUT("user/me/")
    suspend fun updateUser(@Body user: UserResponse): Response<UserResponse>

    @PUT("user/change-password/")
    suspend fun changePassword(@Body changePassword: ChangePassword) : Response<Void>

    @PATCH("user/update-details/")
    suspend fun updateDetails(@Body user: UpdateUser): Response<UserResponse>

}