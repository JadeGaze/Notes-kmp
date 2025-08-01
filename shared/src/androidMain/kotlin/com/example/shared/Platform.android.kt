package com.example.shared

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android LAB hello ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()