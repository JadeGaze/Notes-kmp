package com.example.shared.feature.notes.data.usecase

import com.example.shared.core.utils.runSuspendCatching
import com.example.shared.feature.notes.domain.model.NoteDomainModel
import com.example.shared.feature.notes.domain.repository.NotesRepository
import com.example.shared.feature.notes.domain.usecase.CreateNoteUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CreateNoteUseCaseImpl(
    private val repository: NotesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CreateNoteUseCase {
    override suspend fun invoke(note: NoteDomainModel, isSync: Boolean): Result<String> {
        return runSuspendCatching {
            withContext(dispatcher) {
                repository.createNote(note, isSync)
            }
        }
    }
}