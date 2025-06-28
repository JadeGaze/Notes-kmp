package com.example.shared.feature.notes

import com.example.shared.core.di.viewModel
import com.example.shared.feature.notes.data.datasource.FirebaseNotesDataSource
import com.example.shared.feature.notes.data.datasource.RoomNotesDataSource
import com.example.shared.feature.notes.data.repository.NotesRepositoryImpl
import com.example.shared.feature.notes.data.usecase.CreateNoteUseCaseImpl
import com.example.shared.feature.notes.data.usecase.GetNotesByFolderIdUseCaseImpl
import com.example.shared.feature.notes.domain.datasource.LocalNotesDataSource
import com.example.shared.feature.notes.domain.datasource.RemoteNotesDataSource
import com.example.shared.feature.notes.domain.repository.NotesRepository
import com.example.shared.feature.notes.domain.usecase.CreateNoteUseCase
import com.example.shared.feature.notes.domain.usecase.GetNotesByFolderIdUseCase
import com.example.shared.feature.notes.ui.NotesViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val notesModule = module {
    single<NotesRepository> { NotesRepositoryImpl(get(), get()) }
    single<LocalNotesDataSource> { RoomNotesDataSource(get()) }
    single<RemoteNotesDataSource> { FirebaseNotesDataSource(get()) }

    single<CoroutineDispatcher> { Dispatchers.IO }

    factory<GetNotesByFolderIdUseCase> { GetNotesByFolderIdUseCaseImpl(get(), get()) }
    factory<CreateNoteUseCase> { CreateNoteUseCaseImpl(get(), get()) }

    viewModel { (folderId: String) -> NotesViewModel(get(), get(), folderId) }
}