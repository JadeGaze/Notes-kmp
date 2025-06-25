package com.example.shared

data class Configuration(
    val platformConfiguration: PlatformConfiguration,
    val isHttpLoggingEnabled: Boolean,
    val isDebug: Boolean,
) {

    enum class DeviceType {
        Tablet,
        Phone,
    }
}