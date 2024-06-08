package com.kma_kit.smarthome.data.model.response

data class UserResponse(
    val email: String,
    val first_name: String,
    val last_name: String,
    val date_of_birth: String,
    val gender: Boolean
)