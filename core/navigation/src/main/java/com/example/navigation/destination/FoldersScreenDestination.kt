package com.example.navigation.destination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.example.feature.folders.impl.presentation.ui.FoldersScreen
import com.example.navigation.navigateToNote
import com.example.navigation.navigateToNotes
import com.example.shared.feature.folders.ui.FoldersViewModel
import com.example.shared.feature.folders.ui.model.FoldersContract.Effect
import org.koin.androidx.compose.koinViewModel

@Composable
fun FoldersScreenDestination(navController: NavHostController, userId: String) {
    val viewModel: FoldersViewModel = koinViewModel()
    FoldersScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        navHostController = navController,
        onNavigationRequested = { effect ->

            when (effect) {
                is Effect.Navigation.ToNewNote -> navController.navigateToNote(
                    effect.folderId,
                    effect.noteId
                )

                is Effect.Navigation.ToNotes -> {
                    navController.navigateToNotes(effect.folderId)
                }
            }
        }
    )
}