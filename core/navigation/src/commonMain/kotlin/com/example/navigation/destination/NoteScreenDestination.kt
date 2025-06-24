package com.example.navigation.destination

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import co.touchlab.kermit.Logger
import com.example.impl.presentation.NoteViewModel
import com.example.impl.presentation.model.NoteContract
import com.example.impl.presentation.ui.NoteScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteScreenDestination(navController: NavHostController, folderId: String, noteId: String) {
    Logger.d("NOTES SCREEN CLICK") { "noteID = $noteId\n folderId = $folderId" }

    val viewModel: NoteViewModel = koinViewModel { parametersOf(folderId, noteId) }
    NoteScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { effect ->
            when (effect) {
                NoteContract.Effect.Navigation.ToPrevious -> navController.popBackStack()
            }
        }
    )
}
