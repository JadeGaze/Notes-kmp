package com.example.impl.presentation

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.example.designsystem.BaseViewModel
import com.example.impl.presentation.model.NoteContract.Effect
import com.example.impl.presentation.model.NoteContract.Event
import com.example.impl.presentation.model.NoteContract.UiState
import com.example.shared.feature.folders.data.model.FolderModel
import com.example.shared.feature.note.domain.usecase.GetNoteByIdUseCase
import com.example.shared.feature.note.domain.usecase.UpdateNoteUseCase
import com.example.shared.mapper.toDomain
import com.example.shared.mapper.toUi
import com.example.shared.models.NoteItemUiModel
import kotlinx.coroutines.launch

class NoteViewModel(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val folderId: String,
    private val noteId: String,
) : BaseViewModel<Event, UiState, Effect>() {

    init {
        getNote()
    }

    override fun setInitialState(): UiState {
        return UiState(
            note = NoteItemUiModel(
                id = "0",
                title = "",
                text = "",
                createDate = "",
                editDate = "",
                folder = FolderModel(id = "0", name = "")
            ),
            isLoading = false,
            isError = false
        )
    }

    override fun handleEvents(event: Event) {
        when (event) {
            Event.Empty -> TODO()
            Event.GetNote -> TODO()
            Event.OnBackClicked -> setEffect { Effect.Navigation.ToPrevious }
            Event.Retry -> getNote()
            is Event.SaveNote -> updateNote(event.note)
        }
    }

    private fun updateNote(note: NoteItemUiModel) {
        viewModelScope.launch {
//            setState { copy(isLoading = true, isError = false) }
            updateNoteUseCase.invoke(note.toDomain())
                .onFailure { thr ->
                    Logger.d("NOTE SCREEN") { "SAVE FAILED: ${thr.message}" }
                }
        }
    }

    private fun getNote() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            getNoteByIdUseCase.invoke(noteId)
                .onSuccess { note ->
                    Logger.d("NOTE SCREEN") { note.text }
                    setState { copy(note = note.toUi(), isLoading = false) }
                    setEffect { Effect.NoteWasLoaded }
                }
                .onFailure {
                    setState { copy(isLoading = false, isError = true) }
                    Logger.d("NOTE SCREEN") { "error: ${it}" }
                    setEffect { Effect.DataLoadingError(it.message.orEmpty()) }
                }
        }
    }
}