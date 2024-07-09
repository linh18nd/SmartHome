package com.kma_kit.smarthome.data.model.response

data class Device(
    val id: String,
    val name: String,
    var is_auto: Boolean,
    val device_type: String,
    var value: Double
)