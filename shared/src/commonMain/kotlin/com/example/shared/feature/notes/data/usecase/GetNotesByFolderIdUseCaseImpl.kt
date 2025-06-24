package com.example.shared.feature.notes.data.usecase

import com.example.shared.core.utils.runSuspendCatching
import com.example.shared.feature.notes.domain.model.NoteDomainModel
import com.example.shared.feature.notes.domain.repository.NotesRepository
import com.example.shared.feature.notes.domain.usecase.GetNotesByFolderIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetNotesByFolderIdUseCaseImpl(
    private val repository: NotesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GetNotesByFolderIdUseCase {
    override suspend fun invoke(folderId: String): Result<List<NoteDomainModel>> {
        return runSuspendCatching {
            withContext(dispatcher) {
                repository.getNotesByFolderId(folderId)
            }
        }
    }
}