package com.example.feature.folders.impl

import com.example.feature.folders.impl.presentation.FoldersViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val foldersPresentationModel = module {
    viewModel { FoldersViewModelImpl(get(), get()) }
}