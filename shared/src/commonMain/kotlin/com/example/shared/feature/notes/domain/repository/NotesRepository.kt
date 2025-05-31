package com.example.shared.feature.notes.domain.repository

import com.example.shared.feature.notes.domain.model.NoteDomainModel

interface NotesRepository {
    suspend fun getNotesByFolderId(folderId: String): List<NoteDomainModel>
    suspend fun getNoteById(noteId: Long): NoteDomainModel
    suspend fun createNote(note: NoteDomainModel, isSync: Boolean): String
}