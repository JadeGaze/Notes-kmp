package com.example.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.designsystem.R

@Composable
fun ErrorIconShow(isError: Boolean) {
    if (isError) {
        Icon(
            painter = painterResource(R.drawable.baseline_error_outline_24),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.error
        )
    }
}