package com.example.shared.feature.note.data.usecase

import com.example.shared.core.utils.runSuspendCatching
import com.example.shared.feature.note.domain.repository.NoteRepository
import com.example.shared.feature.note.domain.usecase.UpdateNoteUseCase
import com.example.shared.feature.notes.domain.model.NoteDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class UpdateNoteUseCaseImpl(
    private val repository: NoteRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) :
    UpdateNoteUseCase {
    override suspend fun invoke(note: NoteDomainModel): Result<Unit> {
        return runSuspendCatching {
            withContext(coroutineDispatcher) { repository.updateNote(note) }
        }
    }

}
