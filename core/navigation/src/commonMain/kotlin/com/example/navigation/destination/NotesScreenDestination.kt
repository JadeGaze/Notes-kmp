package com.example.navigation.destination

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import co.touchlab.kermit.Logger
import com.example.navigation.navigateToNote
import com.example.notes.impl.presentation.NotesViewModel
import com.example.notes.impl.presentation.model.NotesContract.Effect
import com.example.notes.impl.presentation.ui.NotesScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun NotesScreenDestination(navController: NavHostController, folderId: String) {
    val viewModel: NotesViewModel = koinViewModel { parametersOf(folderId) }
    NotesScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        navHostController = navController,
        onNavigationRequested = { effect ->
            when (effect) {
                is Effect.Navigation.ToNote -> {
                    Logger.d("NAVIGATION") { "TO NOTE ID = ${effect.noteId}" }
                    navController.navigateToNote(
                        effect.folderId,
                        effect.noteId
                    )
                }

                Effect.Navigation.ToPrevious -> navController.popBackStack()
            }
        }
    )
}