package com.example.impl.presentation.ui

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import com.example.designsystem.SIDE_EFFECTS_KEY
import com.example.designsystem.component.NetworkError
import com.example.designsystem.component.Progress
import com.example.impl.presentation.model.NoteContract
import com.example.impl.presentation.model.NoteContract.Effect
import com.example.impl.presentation.model.NoteContract.Event
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach

@OptIn(FlowPreview::class, ExperimentalComposeUiApi::class)
@Composable
actual fun NoteScreen(
    state: NoteContract.UiState,
    effectFlow: Flow<NoteContract.Effect>?,
    onEventSent: (NoteContract.Event) -> Unit,
    onNavigationRequested: (NoteContract.Effect.Navigation) -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val noteTitle = remember {
        mutableStateOf(state.note.title)
    }
    val noteTitleFlow = remember {
        MutableStateFlow(noteTitle.value)
    }
    val noteText = remember {
        mutableStateOf(state.note.text)
    }
    val noteTextFlow = remember {
        MutableStateFlow(noteText.value)
    }

    LaunchedEffect(state.note) {
        noteTitle.value = state.note.title
        noteText.value = state.note.text
        noteTitleFlow.value = state.note.title
        noteTextFlow.value = state.note.text
    }

    LaunchedEffect(key1 = noteText.value) {
        noteTextFlow.debounce(5000).collectLatest { text ->
            onEventSent(Event.SaveNote(state.note.copy(text = text)))
        }
    }

    LaunchedEffect(key1 = noteTitle.value) {
        noteTitleFlow.debounce(5000).collectLatest { title ->
            onEventSent(Event.SaveNote(state.note.copy(title = title)))
        }
    }

    LaunchedEffect(key1 = SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                Effect.NoteWasLoaded -> {
                    Logger.d("NOTE SCREEN") { "NOTE WAS LOADED" }
                }

                is Effect.DataLoadingError -> Logger.d(NOTE_SCREEN_TAG) { effect.message }
                Effect.NoteWasSave -> {
                    Logger.d(NOTE_SCREEN_TAG) { "note was loaded" }
                }

                Effect.Navigation.ToPrevious -> onNavigationRequested(Effect.Navigation.ToPrevious)
            }
        }?.collect()
        focusRequester.requestFocus()
    }

    when {
        state.isLoading -> Progress()
        state.isError -> NetworkError { onEventSent(Event.Retry) }
        else -> {

            Column(modifier = Modifier.padding(16.dp)) {
                IconButton(
                    onClick = {
                        onEventSent(
                            Event.SaveNote(
                                state.note.copy(
                                    title = noteTitleFlow.value,
                                    text = noteTextFlow.value
                                )
                            )
                        )
                        onEventSent(Event.OnBackClicked)
                    }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
                BasicTextField(
                    value = noteTitle.value,
                    onValueChange = {
                        noteTitle.value = it
                        noteTitleFlow.value = it
                    },
                    singleLine = true,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onKeyEvent {
                            if (it.key == Key.Enter && it.type == KeyEventType.KeyDown) {
                                focusManager.moveFocus(FocusDirection.Next)
                                true
                            } else {
                                false
                            }
                        }
                        .focusable(),
                    textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                )
                BasicTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    value = noteText.value, onValueChange = {
                        noteText.value = it
                        noteTextFlow.value = it
                    }
                )
            }
        }
    }
}