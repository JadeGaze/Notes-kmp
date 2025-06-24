package com.example.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
expect fun NotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
)