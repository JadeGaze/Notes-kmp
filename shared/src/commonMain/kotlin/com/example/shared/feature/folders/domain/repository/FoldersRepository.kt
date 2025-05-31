package com.example.shared.feature.folders.domain.repository

import com.example.shared.core.common.SectionItem
import com.example.shared.feature.folders.data.model.FoldersListModel

interface FoldersRepository {
    suspend fun getAllFolders(): List<FoldersListModel>
    suspend fun getAllLocalFolders(): List<SectionItem>
    suspend fun getAllRemoteFolders(): List<SectionItem>
    suspend fun createFolder(folderName: String, isSync: Boolean): String
}