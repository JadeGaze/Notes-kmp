package com.example.impl.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.designsystem.BaseViewModel
import com.example.impl.presentation.model.NoteContract.Effect
import com.example.impl.presentation.model.NoteContract.Event
import com.example.impl.presentation.model.NoteContract.UiState
import com.example.shared.feature.folders.data.model.FolderModel
import com.example.shared.feature.note.domain.usecase.GetNoteByIdUseCase
import com.example.shared.feature.note.domain.usecase.UpdateNoteUseCase
import com.example.shared.feature.note.ui.NoteViewModel
import com.example.shared.feature.note.ui.models.NoteItemUiModel
import com.example.shared.mapper.toDomain
import com.example.shared.mapper.toUi
import kotlinx.coroutines.launch

class NoteViewModelImpl(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val folderId: String,
    private val noteId: String,
) : BaseViewModel<Event, UiState, Effect>(), NoteViewModel {

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

    override fun updateNote(note: NoteItemUiModel) {
        viewModelScope.launch {
//            setState { copy(isLoading = true, isError = false) }
            updateNoteUseCase.invoke(note.toDomain())
                .onFailure { thr ->
                    Log.d("NOTE SCREEN", "SAVE FAILED: ${thr.message}")
                }
        }
    }

    override fun getNote() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            getNoteByIdUseCase.invoke(noteId)
                .onSuccess { note ->
                    Log.d("NOTE SCREEN", note.text)
                    setState { copy(note = note.toUi(), isLoading = false) }
                    setEffect { Effect.NoteWasLoaded }
                }
                .onFailure {
                    setState { copy(isLoading = false, isError = true) }
                    Log.d("NOTE SCREEN", "error: ${it}")
                    setEffect { Effect.DataLoadingError(it.message.orEmpty()) }
                }
        }
    }
}