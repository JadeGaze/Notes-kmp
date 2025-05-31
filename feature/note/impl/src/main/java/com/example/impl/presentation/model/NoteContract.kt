package com.example.impl.presentation.model

import com.example.designsystem.ViewEvent
import com.example.designsystem.ViewSideEffect
import com.example.designsystem.ViewState
import com.example.shared.models.NoteItemUiModel

class NoteContract {

    sealed class Event : ViewEvent {
        data object GetNote : Event()
        data object Retry : Event()
        data object OnBackClicked : Event()
        data class SaveNote(val note: NoteItemUiModel) : Event()
        data object Empty : Event()
    }

    data class UiState(
        val note: NoteItemUiModel,
        val isLoading: Boolean,
        val isError: Boolean,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NoteWasLoaded : Effect()
        data class DataLoadingError(val message: String) : Effect()
        data object NoteWasSave : Effect()
        sealed class Navigation : Effect() {
            data object ToPrevious : Navigation()
        }
    }

}