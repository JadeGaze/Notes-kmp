package com.example.impl

import com.example.impl.presentation.NoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val notePresentationModule = module {
    viewModel { (folderId: String, noteId: String) ->
        NoteViewModel(
            get(),
            get(),
            folderId,
            noteId
        )
    }
}