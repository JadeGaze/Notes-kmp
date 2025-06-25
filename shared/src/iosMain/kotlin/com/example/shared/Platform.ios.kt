package com.example.shared

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() +
            " " +
            UIDevice.currentDevice.systemVersion  +
            " " +
            "TEST"
}

actual fun getPlatform(): Platform = IOSPlatform()