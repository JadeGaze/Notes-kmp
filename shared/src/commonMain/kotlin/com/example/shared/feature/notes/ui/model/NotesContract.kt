package com.example.shared.feature.notes.ui.model

import com.example.shared.core.presentation.ViewEvent
import com.example.shared.core.presentation.ViewSideEffect
import com.example.shared.core.presentation.ViewState
import com.example.shared.feature.note.ui.models.NoteItemUiModel

class NotesContract {

    sealed class Event : ViewEvent {
        data object GetData : Event()
        data object Empty : Event()
        data object Retry : Event()
        data object OnBackClicked : Event()
        data class OnCreateNewNoteClicked(
            val note: NoteItemUiModel,
            val isSync: Boolean,
        ) : Event()

        data class OnNoteClicked(val folderId: String, val noteId: String) : Event()
    }

    data class UiState(
        val notesList: List<NoteItemUiModel>?,
        val isLoading: Boolean,
        val isError: Boolean,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object DataWasLoaded : Effect()
        data class DataLoadingError(val message: String) : Effect()
        data class NoteCreatingError(val message: String) : Effect()
        sealed class Navigation : Effect() {
            data class ToNote(val folderId: String, val noteId: String) : Navigation()
            data object ToPrevious : Navigation()
        }
    }
}