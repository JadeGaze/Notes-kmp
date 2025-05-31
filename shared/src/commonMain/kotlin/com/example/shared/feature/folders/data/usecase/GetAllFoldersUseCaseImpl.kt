package com.example.shared.feature.folders.data.usecase

import com.example.shared.core.utils.runSuspendCatching
import com.example.shared.feature.folders.data.model.FoldersListModel
import com.example.shared.feature.folders.domain.repository.FoldersRepository
import com.example.shared.feature.folders.domain.usecase.GetAllFoldersUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetAllFoldersUseCaseImpl(
    private val repository: FoldersRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GetAllFoldersUseCase {
    override suspend fun invoke(): Result<List<FoldersListModel>> {
        return runSuspendCatching {
            withContext(dispatcher) { repository.getAllFolders() }
        }
    }
}