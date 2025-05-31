package com.example.shared.feature.note

import com.example.shared.feature.note.data.datasources.RoomNoteDataSource
import com.example.shared.feature.note.data.repository.NoteRepositoryImpl
import com.example.shared.feature.note.data.usecase.GetNoteByIdUseCaseImpl
import com.example.shared.feature.note.data.usecase.UpdateNoteUseCaseImpl
import com.example.shared.feature.note.domain.datasource.LocalNoteDataSource
import com.example.shared.feature.note.domain.repository.NoteRepository
import com.example.shared.feature.note.domain.usecase.GetNoteByIdUseCase
import com.example.shared.feature.note.domain.usecase.UpdateNoteUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val noteModule = module {
    single<NoteRepository> { NoteRepositoryImpl(get(), get()) }
    single<LocalNoteDataSource> { RoomNoteDataSource(get()) }

    single<CoroutineDispatcher> { Dispatchers.IO }

    factory<GetNoteByIdUseCase> { GetNoteByIdUseCaseImpl(get(), get()) }
    factory<UpdateNoteUseCase> { UpdateNoteUseCaseImpl(get(), get()) }
}