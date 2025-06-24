package com.example.notes.impl.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.NoteAdd
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.designsystem.SIDE_EFFECTS_KEY
import com.example.designsystem.component.NetworkError
import com.example.designsystem.component.Progress
import com.example.designsystem.resources.Res
import com.example.designsystem.resources.new_note
import com.example.notes.impl.presentation.model.NotesContract
import com.example.notes.impl.presentation.model.NotesContract.Effect
import com.example.notes.impl.presentation.model.NotesContract.Event
import com.example.notes.impl.presentation.ui.component.NoteItem
import com.example.shared.models.NoteItemUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun NotesScreen(
    state: NotesContract.UiState,
    effectFlow: Flow<NotesContract.Effect>?,
    onEventSent: (NotesContract.Event) -> Unit,
    onNavigationRequested: (NotesContract.Effect.Navigation) -> Unit,
    navHostController: NavHostController
) {
    val title =
        remember { mutableStateOf(if (state.notesList.isNullOrEmpty()) "" else state.notesList[0].title) }
    Scaffold(
        modifier = Modifier.padding(horizontal = 16.dp),

        topBar = {
            Log.d("NOTES SCREEN", "title = ${title.value}")
            TopAppBar(
                title = { Text(title.value) },
                navigationIcon = {
                    IconButton(onClick = { onEventSent(Event.OnBackClicked) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
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
                        Res.string.new_note
                    )
                    Image(
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
                        imageVector = Icons.Outlined.NoteAdd,
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
                    if (state.notesList.isNullOrEmpty()) "" else state.notesList[0].folder.name
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
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        itemsIndexed(items = notes, key = { _, note -> note.id }) { index, note ->
            val modifier = when (index) {
                0 -> {
                    Modifier.clip(RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
                }

                notes.lastIndex -> {
                    Modifier.clip(RoundedCornerShape(0.dp, 0.dp, 24.dp, 24.dp))
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