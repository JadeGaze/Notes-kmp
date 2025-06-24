package com.example.shared.feature.folders.domain.usecase

import com.example.shared.feature.folders.data.model.FoldersListModel

interface GetAllFoldersUseCase {
    suspend operator fun invoke(): Result<List<FoldersListModel>>
}