package com.example.shared.feature.notes.ui

import com.example.shared.feature.note.ui.models.NoteItemUiModel

interface NotesViewModel {

    fun createNewNote(note: NoteItemUiModel, isSync: Boolean = false)

    fun getNotesData(folderId: String)
}