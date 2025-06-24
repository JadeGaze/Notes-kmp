package com.example.feature.folders.impl.presentation

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.example.designsystem.BaseViewModel
import com.example.feature.folders.impl.mapper.toUi
import com.example.feature.folders.impl.presentation.model.FolderUiModel
import com.example.feature.folders.impl.presentation.model.FoldersContract
import com.example.feature.folders.impl.presentation.ui.FOLDER_SCREEN_TAG
import com.example.shared.feature.folders.domain.usecase.CreateFolderUseCase
import com.example.shared.feature.folders.domain.usecase.GetAllFoldersUseCase
import kotlinx.coroutines.launch

class FoldersViewModel(
    private val getAllFoldersUseCase: GetAllFoldersUseCase,
    private val createFolderUseCase: CreateFolderUseCase,
) : BaseViewModel<FoldersContract.Event, FoldersContract.UiState, FoldersContract.Effect>() {

    init {
        getFoldersData()
    }

    override fun setInitialState(): FoldersContract.UiState {
        return FoldersContract.UiState(
            sectionData = listOf(), isLoading = false, isError = false
        )
    }

    override fun handleEvents(event: FoldersContract.Event) {
        when (event) {
            FoldersContract.Event.GetData -> getFoldersData()
            is FoldersContract.Event.OnFolderClicked -> setEffect {
                FoldersContract.Effect.Navigation.ToNotes(event.folderId)
            }

            is FoldersContract.Event.OnCreateNewNoteClicked -> setEffect {
                FoldersContract.Effect.Navigation.ToNewNote(event.folderId, event.noteId)
            }

            is FoldersContract.Event.OnCreateNewFolderClicked -> createNewFolder(event.folderName, event.isSync)
            FoldersContract.Event.Retry -> getFoldersData()
            FoldersContract.Event.Empty -> setEffect { FoldersContract.Effect.Empty }
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
                    setEffect { FoldersContract.Effect.FolderWasCreated }
                }
                .onFailure { thr ->
                    setState { copy(isLoading = false, isError = true) }
                    setEffect { FoldersContract.Effect.FolderCreateError(thr.message.orEmpty()) }
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
                    setEffect { FoldersContract.Effect.DataWasLoaded }
                }
                .onFailure { thr ->
                    setState {
                        copy(isLoading = false, isError = true)
                    }
                    setEffect { FoldersContract.Effect.DataLoadingError(thr.message.orEmpty()) }
                    Logger.d(thr, FOLDER_SCREEN_TAG) { "AAAAAAAAAAAAAAAAAA: $thr" }

                }
        }


    }

}