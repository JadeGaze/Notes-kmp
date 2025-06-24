package com.example.feature.folders.impl.presentation.model

import com.example.designsystem.SectionData
import com.example.designsystem.ViewEvent
import com.example.designsystem.ViewSideEffect
import com.example.designsystem.ViewState

class FoldersContract {

    sealed class Event : ViewEvent {
        data object GetData : Event()
        data object Retry : Event()
        data class OnCreateNewFolderClicked(val folderName: String, val isSync: Boolean) : Event()
        data class OnCreateNewNoteClicked(val folderId: String, val noteId: String) : Event()
        data class OnFolderClicked(val folderId: String) : Event()

        data object Empty : Event()
    }

    data class UiState(
        val sectionData: List<SectionData>,
        val isLoading: Boolean,
        val isError: Boolean,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object Empty : Effect()
        data object DataWasLoaded : Effect()
        data class DataLoadingError(val message: String) : Effect()
        data object FolderWasCreated : Effect()
        data class FolderCreateError(val message: String) : Effect()
        sealed class Navigation : Effect() {
            data class ToNewNote(val folderId: String, val noteId: String) : Navigation()
            data class ToNotes(val folderId: String) : Navigation()
        }
    }

}