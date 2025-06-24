package com.example.notes.impl

import com.example.notes.impl.presentation.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notesPresentationModule = module {
    viewModel { (folderId: String) -> NotesViewModel(get(), get(), folderId) }
}