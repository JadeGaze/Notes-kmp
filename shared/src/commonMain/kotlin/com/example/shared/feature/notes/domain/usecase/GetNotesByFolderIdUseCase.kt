package com.example.shared.feature.notes.domain.usecase

import com.example.shared.feature.notes.domain.model.NoteDomainModel

interface GetNotesByFolderIdUseCase {

    suspend operator fun invoke(folderId: String): Result<List<NoteDomainModel>>

}