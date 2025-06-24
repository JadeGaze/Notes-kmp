package com.example.shared.feature.note.data.repository

import com.example.shared.core.utils.isDigitsOnly
import com.example.shared.feature.note.domain.datasource.LocalNoteDataSource
import com.example.shared.feature.note.domain.repository.NoteRepository
import com.example.shared.feature.notes.domain.datasource.RemoteNotesDataSource
import com.example.shared.feature.notes.domain.mapper.toData
import com.example.shared.feature.notes.domain.mapper.toDomain
import com.example.shared.feature.notes.domain.mapper.toEntity
import com.example.shared.feature.notes.domain.model.NoteDomainModel

class NoteRepositoryImpl(
    private val localDataSource: LocalNoteDataSource,
    private val remoteDataSource: RemoteNotesDataSource,
) : NoteRepository {
    override suspend fun updateNote(note: NoteDomainModel) {
        if (note.id.isDigitsOnly()) {
            localDataSource.saveNote(note.toEntity())
        } else {
            remoteDataSource.updateNote(note.toData())
        }
    }

    override suspend fun getNoteById(noteId: String): NoteDomainModel {
        return try {
            localDataSource.getNoteById(noteId.toLong()).toDomain()
        } catch (e: Exception) {
            remoteDataSource.getNoteById(noteId).toDomain()
        }
    }
}