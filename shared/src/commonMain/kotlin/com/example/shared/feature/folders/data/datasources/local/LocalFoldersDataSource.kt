package com.example.shared.feature.folders.data.datasources.local

import com.example.shared.core.database.model.FolderDataModel

interface LocalFoldersDataSource {
    suspend fun getAllLocalFolders(): List<FolderDataModel>
    suspend fun getLocalFolderById(id: String): FolderDataModel
    suspend fun createFolder(folderName: String): Long
}