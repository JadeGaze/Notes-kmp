package com.example.shared.feature.note.domain.datasource

import com.example.shared.core.database.entity.NoteEntity
import com.example.shared.core.database.entity.NoteWithFolderEntity

interface LocalNoteDataSource {
    suspend fun saveNote(note: NoteEntity)

    suspend fun getNoteById(noteId: Long): NoteWithFolderEntity
}