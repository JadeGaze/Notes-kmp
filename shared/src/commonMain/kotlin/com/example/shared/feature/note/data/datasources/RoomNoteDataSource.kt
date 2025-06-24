package com.example.shared.feature.note.data.datasources

import com.example.shared.core.database.dao.NotesDao
import com.example.shared.core.database.entity.NoteEntity
import com.example.shared.core.database.entity.NoteWithFolderEntity
import com.example.shared.feature.note.domain.datasource.LocalNoteDataSource

class RoomNoteDataSource(
    private val notesDao: NotesDao,
) : LocalNoteDataSource {
    override suspend fun saveNote(note: NoteEntity) {
        notesDao.updateNote(note)
    }

    override suspend fun getNoteById(noteId: Long): NoteWithFolderEntity {
        return notesDao.getNoteById(noteId)
    }
}