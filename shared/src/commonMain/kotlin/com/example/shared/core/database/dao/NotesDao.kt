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
    fun getNotesByFolderId(folderId: Long): List<NoteWithFolderEntity>

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Long): NoteWithFolderEntity

    @Insert
    fun createNote(note: NoteEntity): Long

    @Update
    fun updateNote(note: NoteEntity)
}