package com.example.notes.impl.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.designsystem.BaseViewModel
import com.example.notes.impl.presentation.model.NotesContract.Effect
import com.example.notes.impl.presentation.model.NotesContract.Event
import com.example.notes.impl.presentation.model.NotesContract.UiState
import com.example.notes.impl.presentation.ui.NOTES_SCREEN_TAG
import com.example.shared.core.utils.resource.TimeUtil
import com.example.shared.feature.note.ui.models.NoteItemUiModel
import com.example.shared.feature.notes.domain.usecase.CreateNoteUseCase
import com.example.shared.feature.notes.domain.usecase.GetNotesByFolderIdUseCase
import com.example.shared.feature.notes.ui.NotesViewModel
import com.example.shared.mapper.toDomain
import com.example.shared.mapper.toUi
import kotlinx.coroutines.launch

class NotesViewModelImpl(
    private val getNotesByFolderIdUseCase: GetNotesByFolderIdUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val folderId: String,
) :
    BaseViewModel<Event, UiState, Effect>(), NotesViewModel {

    init {
        getNotesData(folderId)
    }

    override fun setInitialState(): UiState {
        return UiState(notesList = null, isLoading = false, isError = false)
    }

    override fun handleEvents(event: Event) {
        Log.d("NOTES_SCREEN_TAG", "HANDLE ${event}")
        when (event) {
            is Event.GetData -> getNotesData(folderId)
            Event.OnBackClicked -> setEffect {
                Effect.Navigation.ToPrevious
            }

            is Event.OnCreateNewNoteClicked -> createNewNote(
                event.note
            )

            is Event.OnNoteClicked -> setEffect {
                Effect.Navigation.ToNote(event.folderId, event.noteId)
            }

            is Event.Retry -> getNotesData(folderId)
            Event.Empty -> {}
        }
    }

    override fun createNewNote(
        note: NoteItemUiModel,
        isSync: Boolean,
    ) {
        viewModelScope.launch {
            val currentTime = TimeUtil.currentTimeUtc()
            createNoteUseCase.invoke(
                note.copy(folder = note.folder.copy(id = folderId)).toDomain().copy(
                    createDate = currentTime,
                    editDate = currentTime
                ),
                isSync
            )
                .onSuccess { noteId ->
                    Log.d(NOTES_SCREEN_TAG, "NOTE ID: ${noteId}")
                    setEffect { Effect.Navigation.ToNote(folderId, noteId) }
                    setState { copy(isLoading = false, isError = false) }
                }
                .onFailure {
                    Log.d(NOTES_SCREEN_TAG, "EXCEPTION: ${it}")
                    setEffect { Effect.NoteCreatingError(it.message ?: "Something went wrong") }
                    setState { copy(isLoading = false, isError = true) }
                }
        }
    }

    override fun getNotesData(folderId: String) {

        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            getNotesByFolderIdUseCase.invoke(folderId)
                .onSuccess { list ->
                    setState {
                        copy(
                            notesList = list.toUi(),
                            isLoading = false,
                            isError = false
                        )
                    }
                    setEffect { Effect.DataWasLoaded }

                }
                .onFailure { thr ->
                    setState { copy(isLoading = false, isError = true) }
                    Log.d("NOTES SCREEN", "error: ${thr}")
                    setEffect { Effect.DataLoadingError(thr.message ?: "Something went wrong") }
                }
        }
    }
}