package com.example.impl

import com.example.impl.presentation.NoteViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notePresentationModule = module {
    viewModel { (folderId: String, noteId: String) ->
        NoteViewModelImpl(
            get(),
            get(),
            folderId,
            noteId
        )
    }
}