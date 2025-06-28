package com.example.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.designsystem.theme.Typography

@Composable
fun FormButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = buttonText,
            style = Typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
        )
    }
}