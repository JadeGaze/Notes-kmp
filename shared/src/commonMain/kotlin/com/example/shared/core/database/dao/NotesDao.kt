package com.example.shared.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.shared.core.database.entity.NoteEntity
import com.example.shared.core.database.entity.NoteWithFolderEntity

@Dao
interface NotesDao {

    @Transaction
    @Query("SELECT * FROM notes WHERE folder_id = :folderId")
    suspend fun getNotesByFolderId(folderId: Long): List<NoteWithFolderEntity>

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Long): NoteWithFolderEntity

    @Insert
    suspend fun createNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)
}