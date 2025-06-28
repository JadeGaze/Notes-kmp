package com.example.shared.feature.folders.ui

import androidx.lifecycle.viewModelScope
import com.example.shared.core.presentation.BaseViewModel
import com.example.shared.core.utils.mapper.toUi
import com.example.shared.feature.folders.domain.usecase.CreateFolderUseCase
import com.example.shared.feature.folders.domain.usecase.GetAllFoldersUseCase
import com.example.shared.feature.folders.ui.model.FolderUiModel
import com.example.shared.feature.folders.ui.model.FoldersContract.UiState
import com.example.shared.feature.folders.ui.model.FoldersContract.Effect
import com.example.shared.feature.folders.ui.model.FoldersContract.Event
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.launch

class FoldersViewModel(
    private val getAllFoldersUseCase: GetAllFoldersUseCase,
    private val createFolderUseCase: CreateFolderUseCase,
) : BaseViewModel<Event, UiState, Effect>() {

    init {
        getFoldersData()
    }

    override fun setInitialState(): UiState {
        return UiState(
            sectionData = listOf(), isLoading = false, isError = false
        )
    }

    override fun handleEvents(event: Event) {
        when (event) {
            Event.GetData -> getFoldersData()
            is Event.OnFolderClicked -> setEffect {
                Effect.Navigation.ToNotes(event.folderId)
            }

            is Event.OnCreateNewNoteClicked -> setEffect {
                Effect.Navigation.ToNewNote(event.folderId, event.noteId)
            }

            is Event.OnCreateNewFolderClicked -> createNewFolder(event.folderName, event.isSync)
            Event.Retry -> getFoldersData()
            Event.Empty -> setEffect { Effect.Empty }
        }
    }

    private fun createNewFolder(folderName: String, isSync: Boolean) {

        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            createFolderUseCase.invoke(folderName, isSync)
                .onSuccess { id ->
                    setState {
                        if (isSync) {
                            copy(
                                sectionData = sectionData.apply {
                                    find { it.header == "Remote folders" }!!.items.apply {
                                        add(
                                            FolderUiModel(
                                                id = id,
                                                name = folderName,
                                                notesNumber = 0
                                            )
                                        )
                                    }
                                },
                                isLoading = false,
                                isError = false
                            )
                        } else {
                            copy(
                                sectionData = sectionData.apply {
                                    find { it.header == "Local folders" }!!.items.apply {
                                        add(
                                            FolderUiModel(
                                                id = id,
                                                name = folderName,
                                                notesNumber = 0
                                            )
                                        )
                                    }
                                },
                                isLoading = false,
                                isError = false
                            )
                        }
                    }
                    setEffect { Effect.FolderWasCreated }
                }
                .onFailure { thr ->
                    setState { copy(isLoading = false, isError = true) }
                    setEffect { Effect.FolderCreateError(thr.message.orEmpty()) }
                }
        }


    }

    private fun getFoldersData() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }
            getAllFoldersUseCase.invoke()
                .onSuccess { list ->
                    setState {
                        copy(
                            sectionData = list.toUi(),
                            isLoading = false,
                            isError = false
                        )
                    }
                    setEffect { Effect.DataWasLoaded }
                }
                .onFailure { thr ->
                    setState {
                        copy(isLoading = false, isError = true)
                    }
                    setEffect { Effect.DataLoadingError(thr.message.orEmpty()) }
//                    Log.d(FOLDER_SCREEN_TAG, "AAAAAAAAAAAAAAAAAA: $thr")

                }
        }


    }

}