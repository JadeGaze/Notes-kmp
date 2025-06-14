package com.example.navigation.destination

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.example.navigation.navigateToNote
import com.example.notes.impl.presentation.ui.NotesScreen
import com.example.shared.feature.notes.ui.NotesViewModel
import com.example.shared.feature.notes.ui.model.NotesContract.Effect
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun NotesScreenDestination(navController: NavHostController, folderId: String) {
    val viewModel: NotesViewModel = koinViewModel { parametersOf(folderId) }
    NotesScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        navHostController = navController,
        onNavigationRequested = { effect ->
            when (effect) {
                is Effect.Navigation.ToNote -> {
                    Log.d("NAVIGATION", "TO NOTE ID = ${effect.noteId}")
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