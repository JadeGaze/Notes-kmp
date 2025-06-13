package com.example.shared.feature.note.ui

import com.example.shared.feature.note.ui.models.NoteItemUiModel


interface NoteViewModel {

    fun updateNote(note: NoteItemUiModel)

    fun getNote()
}