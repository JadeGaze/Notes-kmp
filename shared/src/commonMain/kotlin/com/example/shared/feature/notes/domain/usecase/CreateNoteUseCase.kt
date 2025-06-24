package com.example.shared.feature.notes.domain.usecase

import com.example.shared.feature.notes.domain.model.NoteDomainModel

interface CreateNoteUseCase {

    suspend operator fun invoke(note: NoteDomainModel, isSync: Boolean): Result<String>

}