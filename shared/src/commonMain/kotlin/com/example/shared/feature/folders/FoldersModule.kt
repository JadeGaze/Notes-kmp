package com.example.shared.feature.folders

import com.example.shared.core.di.viewModel
import com.example.shared.feature.folders.data.datasources.local.LocalFoldersDataSource
import com.example.shared.feature.folders.data.datasources.local.RoomFoldersDataSource
import com.example.shared.feature.folders.data.datasources.remote.FirebaseFoldersDataSource
import com.example.shared.feature.folders.data.datasources.remote.RemoteFoldersDataSource
import com.example.shared.feature.folders.data.repository.FoldersRepositoryImpl
import com.example.shared.feature.folders.data.usecase.CreateFolderUseCaseImpl
import com.example.shared.feature.folders.data.usecase.GetAllFoldersUseCaseImpl
import com.example.shared.feature.folders.domain.repository.FoldersRepository
import com.example.shared.feature.folders.domain.usecase.CreateFolderUseCase
import com.example.shared.feature.folders.domain.usecase.GetAllFoldersUseCase
import com.example.shared.feature.folders.ui.FoldersViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val foldersModule = module {

    single<LocalFoldersDataSource> { RoomFoldersDataSource(get()) }
    single<RemoteFoldersDataSource> { FirebaseFoldersDataSource(get(), get()) }
    single<FoldersRepository> { FoldersRepositoryImpl(get(), get()) }

    single<CoroutineDispatcher> { Dispatchers.IO }

    factory<GetAllFoldersUseCase> { GetAllFoldersUseCaseImpl(get(), get()) }
    factory<CreateFolderUseCase> { CreateFolderUseCaseImpl(get(), get()) }

    viewModel { FoldersViewModel(get(), get()) }

}