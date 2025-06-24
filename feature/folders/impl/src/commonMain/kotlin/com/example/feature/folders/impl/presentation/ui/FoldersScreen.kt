package com.example.feature.folders.impl.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.feature.folders.impl.presentation.model.FoldersContract.Effect
import com.example.feature.folders.impl.presentation.model.FoldersContract.Event
import com.example.feature.folders.impl.presentation.model.FoldersContract.UiState
import kotlinx.coroutines.flow.Flow

@Composable
expect fun FoldersScreen(
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    navHostController: NavHostController,
    onNavigationRequested: (navigationEffect: Effect.Navigation) -> Unit,
)

const val FOLDER_SCREEN_TAG = "FolderScreenTag"