package com.example.impl.presentation.ui

import androidx.compose.runtime.Composable
import com.example.impl.presentation.model.NoteContract.Effect
import com.example.impl.presentation.model.NoteContract.Event
import com.example.impl.presentation.model.NoteContract.UiState
import kotlinx.coroutines.flow.Flow

@Composable
expect fun NoteScreen(
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    onNavigationRequested: (navigationEffect: Effect.Navigation) -> Unit,
)

const val NOTE_SCREEN_TAG = "NOTE_SCREEN_TAG"