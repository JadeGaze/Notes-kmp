package com.example.shared.feature.notes.data.repository

import com.example.shared.feature.notes.domain.datasource.LocalNotesDataSource
import com.example.shared.feature.notes.domain.datasource.RemoteNotesDataSource
import com.example.shared.feature.notes.domain.mapper.toData
import com.example.shared.feature.notes.domain.mapper.toDomain
import com.example.shared.feature.notes.domain.mapper.toDomainFromEntity
import com.example.shared.feature.notes.domain.mapper.toEntity
import com.example.shared.feature.notes.domain.model.NoteDomainModel
import com.example.shared.feature.notes.domain.repository.NotesRepository

class NotesRepositoryImpl(
    private val localDataSource: LocalNotesDataSource,
    private val remoteDataSource: RemoteNotesDataSource,
) : NotesRepository {
    override suspend fun getNotesByFolderId(folderId: String): List<NoteDomainModel> {
        return try {
            localDataSource.getNotesByFolderId(folderId.toLong()).toDomainFromEntity()
        } catch (e: NumberFormatException) {
            remoteDataSource.getNotesByFolderId(folderId).toDomain()
        }
    }

    override suspend fun getNoteById(noteId: Long): NoteDomainModel {
        return localDataSource.getNoteById(noteId).toDomain()
    }

    override suspend fun createNote(note: NoteDomainModel, isSync: Boolean): String {
        return try {
            note.folder.id.toLong()
            localDataSource.createNote(note.toEntity()).toString()
        } catch (e: Exception) {
            remoteDataSource.createNote(note.toData())
        }
    }
}