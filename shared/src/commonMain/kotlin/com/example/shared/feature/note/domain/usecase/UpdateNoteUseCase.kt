package com.example.shared.feature.note.domain.usecase

import com.example.shared.feature.notes.domain.model.NoteDomainModel

interface UpdateNoteUseCase {
    suspend operator fun invoke(note: NoteDomainModel): Result<Unit>
}
