package com.example.auth.impl.presentation.ui

import androidx.compose.runtime.Composable
import com.example.auth.impl.presentation.model.SignInContract.Effect
import com.example.auth.impl.presentation.model.SignInContract.Event
import com.example.auth.impl.presentation.model.SignInContract.UiState
import kotlinx.coroutines.flow.Flow


@Composable
expect fun SignInScreen(
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    onNavigationRequested: (navigationEffect: Effect.Navigation) -> Unit,
)