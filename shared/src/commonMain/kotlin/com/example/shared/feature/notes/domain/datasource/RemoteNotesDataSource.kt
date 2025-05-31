package com.example.shared.feature.notes.domain.datasource

import com.example.shared.core.database.model.NoteDataModel
import com.example.shared.core.database.model.NoteWithFolderDataModel

interface RemoteNotesDataSource {
    suspend fun getNotesByFolderId(folderId: String): List<NoteWithFolderDataModel>
    suspend fun getNoteById(noteId: String): NoteWithFolderDataModel
    suspend fun createNote(note: NoteDataModel): String
    suspend fun updateNote(note: NoteDataModel): String
}