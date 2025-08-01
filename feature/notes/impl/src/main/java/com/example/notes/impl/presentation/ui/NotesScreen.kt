package com.example.notes.impl.presentation.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.designsystem.R
import com.example.designsystem.SIDE_EFFECTS_KEY
import com.example.designsystem.component.status.NetworkError
import com.example.designsystem.component.status.Progress
import com.example.designsystem.theme.ExtraShapes.FirstItemShape
import com.example.designsystem.theme.ExtraShapes.LastItemShape
import com.example.shared.feature.note.ui.models.NoteItemUiModel
import com.example.shared.feature.notes.ui.model.NotesContract.UiState
import com.example.shared.feature.notes.ui.model.NotesContract.Event
import com.example.shared.feature.notes.ui.model.NotesContract.Effect
import com.example.notes.impl.presentation.ui.component.NoteItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    state: UiState,
    effectFlow: Flow<Effect>?,
    onEventSent: (event: Event) -> Unit,
    onNavigationRequested: (navigationEffect: Effect.Navigation) -> Unit,
    navHostController: NavHostController,
) {
    val title =
        remember { mutableStateOf(if (state.notesList.isNullOrEmpty()) "" else state.notesList!![0].title) } // почему-то начало ругаться после переноса view model в общую часть
    Scaffold(
        topBar = {
            Log.d("NOTES SCREEN", "title = ${title.value}")
            TopAppBar(title = { Text(title.value) })
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {
                    val titleNewNote = stringResource(
                        id = R.string.new_note
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            Log.d(NOTES_SCREEN_TAG, "add note clicked")
                            onEventSent(
                                Event.OnCreateNewNoteClicked(
                                    note = NoteItemUiModel.getDefault().copy(
                                        title = titleNewNote
                                    ), isSync = false
                                )
                            )
                        },
                        painter = painterResource(id = R.drawable.outline_note_add_24),
                        contentDescription = ""
                    )
                }
            }
        }
    ) { paddingValues ->

        LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
            effectFlow?.onEach { effect ->
                when (effect) {
                    Effect.DataWasLoaded -> Log.d(NOTES_SCREEN_TAG, "Notes was loaded")
                    is Effect.Navigation.ToNote -> onNavigationRequested(effect)
                    is Effect.Navigation.ToPrevious -> onNavigationRequested(effect)
                    is Effect.DataLoadingError -> Log.d(NOTES_SCREEN_TAG, effect.message)
                    is Effect.NoteCreatingError -> Log.d(NOTES_SCREEN_TAG, effect.message)
                }
            }?.collect()
        }

        LaunchedEffect(key1 = navHostController) {
            onEventSent(Event.GetData)
        }

        when {
            state.isLoading -> {
                Log.d(NOTES_SCREEN_TAG, "IS LOADING")
                Progress()
            }

            state.isError -> {
                Log.d(NOTES_SCREEN_TAG, "IS ERROR")
                NetworkError { onEventSent(Event.Retry) }
            }

            else -> {
                title.value =
                    if (state.notesList.isNullOrEmpty()) "" else state.notesList!![0].folder.name // почему-то начало ругаться после переноса view model в общую часть
                Log.d(NOTES_SCREEN_TAG, "IS SHOWING ${title.value}")
                NotesContent(paddingValues, state.notesList.orEmpty()) { noteId, folderId ->
                    onEventSent(Event.OnNoteClicked(noteId = noteId, folderId = folderId))
                }
            }
        }

    }

}

@Composable
fun NotesContent(
    paddingValues: PaddingValues,
    notes: List<NoteItemUiModel>,
    onItemClick: (String, String) -> Unit,
) {
    LazyColumn(modifier = Modifier
        .padding(paddingValues)
        .padding(horizontal = 16.dp)) {
        itemsIndexed(items = notes, key = { _, note -> note.id }) { index, note ->
            val modifier = when (index) {
                0 -> {
                    Modifier.clip(FirstItemShape)
                }

                notes.lastIndex -> {
                    Modifier.clip(LastItemShape)
                }

                else -> {
                    Modifier
                }
            }
            NoteItem(
                modifier = modifier,
                note = note,
                isDivider = notes.lastIndex != index,
                onItemClick = onItemClick
            )
        }
    }
}

const val NOTES_SCREEN_TAG = "NOTES_SCREEN_TAG"