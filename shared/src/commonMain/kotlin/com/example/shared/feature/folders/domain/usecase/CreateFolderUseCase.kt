package com.example.shared.feature.folders.domain.usecase

interface CreateFolderUseCase {
    suspend operator fun invoke(folderName: String, isSync: Boolean): Result<String>
}