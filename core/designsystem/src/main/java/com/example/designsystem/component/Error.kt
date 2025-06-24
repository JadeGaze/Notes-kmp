package com.example.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NetworkError(
    modifier: Modifier = Modifier,
    onRetryButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )

        Text(
            text = "Error Description",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
        )
        Button(onClick = { onRetryButtonClick() }) {
            Text(
                text = "retry"
            )
        }
    }
}