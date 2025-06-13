package com.example.notes.impl

import com.example.notes.impl.presentation.NotesViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val notesPresentationModule = module {
    viewModel { (folderId: String) -> NotesViewModelImpl(get(), get(), folderId) }
}