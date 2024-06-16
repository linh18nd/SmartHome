package com.kma_kit.smarthome.data.model.request

data class UserRegistration(
    val email: String,
    val password: String,
    val date_of_birth: String,
    val is_staff: Boolean,
    val gender: Boolean,
    val first_name: String,
    val last_name: String,
    val fcm_token: String
)