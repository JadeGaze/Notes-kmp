package com.example.shared.feature.folders.data.usecase

import com.example.shared.core.utils.runSuspendCatching
import com.example.shared.feature.folders.domain.repository.FoldersRepository
import com.example.shared.feature.folders.domain.usecase.CreateFolderUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CreateFolderUseCaseImpl(
    private val repository: FoldersRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CreateFolderUseCase {
    override suspend fun invoke(folderName: String, isSync: Boolean): Result<String> {
        return runSuspendCatching {
            withContext(dispatcher) { repository.createFolder(folderName, isSync) }
        }
    }
}