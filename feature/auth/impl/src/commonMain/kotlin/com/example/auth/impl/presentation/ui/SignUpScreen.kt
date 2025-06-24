package com.example.auth.impl.presentation.ui

import androidx.compose.runtime.Composable
import com.example.auth.impl.presentation.model.SignUpContract.Effect
import com.example.auth.impl.presentation.model.SignUpContract.Event
import com.example.auth.impl.presentation.model.SignUpContract.UiState
import kotlinx.coroutines.flow.Flow

@Composable
expect fun SignUpScreen(
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    onNavigationRequested: (navigationEffect: Effect.Navigation) -> Unit,
)