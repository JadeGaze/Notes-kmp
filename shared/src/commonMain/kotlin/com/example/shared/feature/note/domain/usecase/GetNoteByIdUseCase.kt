package com.example.shared.feature.note.domain.usecase

import com.example.shared.feature.notes.domain.model.NoteDomainModel

interface GetNoteByIdUseCase {
    suspend operator fun invoke(noteId: String): Result<NoteDomainModel>
}