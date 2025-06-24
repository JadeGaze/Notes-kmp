package com.example.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
actual fun ErrorIconShow(isError: Boolean) {
    if (isError) {
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.error
        )
    }
}