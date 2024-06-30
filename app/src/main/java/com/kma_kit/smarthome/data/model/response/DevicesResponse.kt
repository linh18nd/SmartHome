package com.kma_kit.smarthome.data.model.response

data class Device(
    val id: String,
    val name: String,
    var isAuto: Boolean,
    val deviceType: String,
    val value: Double
)