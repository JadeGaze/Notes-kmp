package com.example.shared.feature.notes.domain.datasource

import com.example.shared.core.database.entity.NoteEntity
import com.example.shared.core.database.entity.NoteWithFolderEntity

interface LocalNotesDataSource {
    suspend fun getNotesByFolderId(folderId: Long): List<NoteWithFolderEntity>
    suspend fun getNoteById(noteId: Long): NoteWithFolderEntity
    suspend fun createNote(note: NoteEntity): Long

}