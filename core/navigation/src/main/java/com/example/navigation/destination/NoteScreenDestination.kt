package com.example.navigation.destination

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.example.impl.presentation.ui.NoteScreen
import com.example.shared.feature.note.ui.NoteViewModel
import com.example.shared.feature.note.ui.models.NoteContract.Effect
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NoteScreenDestination(navController: NavHostController, folderId: String, noteId: String) {
    Log.d("NOTES SCREEN CLICK", "noteID = $noteId\n folderId = $folderId")

    val viewModel: NoteViewModel = koinViewModel { parametersOf(folderId, noteId) }
    NoteScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { effect ->
            when (effect) {
                Effect.Navigation.ToPrevious -> navController.popBackStack()
            }
        }
    )
}
