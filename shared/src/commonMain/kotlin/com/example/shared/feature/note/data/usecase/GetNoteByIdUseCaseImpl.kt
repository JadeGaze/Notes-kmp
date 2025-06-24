package com.example.shared.feature.note.data.usecase

import com.example.shared.core.utils.runSuspendCatching
import com.example.shared.feature.note.domain.repository.NoteRepository
import com.example.shared.feature.note.domain.usecase.GetNoteByIdUseCase
import com.example.shared.feature.notes.domain.model.NoteDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetNoteByIdUseCaseImpl(
    private val repository: NoteRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GetNoteByIdUseCase {
    override suspend fun invoke(noteId: String): Result<NoteDomainModel> {
        return runSuspendCatching {
            withContext(dispatcher) {
                repository.getNoteById(noteId)
            }
        }
    }
}