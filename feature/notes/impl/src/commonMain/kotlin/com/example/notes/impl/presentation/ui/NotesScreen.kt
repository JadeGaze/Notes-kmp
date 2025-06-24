package com.example.notes.impl.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.notes.impl.presentation.model.NotesContract.Effect
import com.example.notes.impl.presentation.model.NotesContract.Event
import com.example.notes.impl.presentation.model.NotesContract.UiState
import kotlinx.coroutines.flow.Flow

@Composable
expect fun NotesScreen(
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    onNavigationRequested: (navigationEffect: Effect.Navigation) -> Unit,
    navHostController: NavHostController,
)

const val NOTES_SCREEN_TAG = "NOTES_SCREEN_TAG"