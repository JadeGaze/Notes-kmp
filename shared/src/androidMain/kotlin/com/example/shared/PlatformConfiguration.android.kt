package com.example.shared

import android.content.Context

actual class PlatformConfiguration(
    val androidContext: Context,
    actual val appVersionName: String,
    actual val appVersionNumber: String,
    actual val osVersion: String,
    actual val deviceType: Configuration.DeviceType
)