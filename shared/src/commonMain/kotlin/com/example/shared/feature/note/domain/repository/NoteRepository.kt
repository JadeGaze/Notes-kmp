package com.example.shared.feature.note.domain.repository

import com.example.shared.feature.notes.domain.model.NoteDomainModel

interface NoteRepository {

    suspend fun updateNote(note: NoteDomainModel)

    suspend fun getNoteById(noteId: String): NoteDomainModel

}