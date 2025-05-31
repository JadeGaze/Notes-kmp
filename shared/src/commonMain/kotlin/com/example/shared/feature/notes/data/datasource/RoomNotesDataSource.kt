package com.example.shared.feature.notes.data.datasource

import com.example.shared.core.database.dao.NotesDao
import com.example.shared.core.database.entity.NoteEntity
import com.example.shared.core.database.entity.NoteWithFolderEntity
import com.example.shared.feature.notes.domain.datasource.LocalNotesDataSource

class RoomNotesDataSource(
    private val notesDao: NotesDao,
) : LocalNotesDataSource {
    override suspend fun getNotesByFolderId(folderId: Long): List<NoteWithFolderEntity> {
        return notesDao.getNotesByFolderId(folderId)
    }

    override suspend fun getNoteById(noteId: Long): NoteWithFolderEntity {
        return notesDao.getNoteById(noteId)
    }

    override suspend fun createNote(note: NoteEntity): Long {
        return notesDao.createNote(note)
    }
}