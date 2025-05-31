package com.example.shared.feature.folders.data.datasources.remote

import com.example.shared.core.database.model.FolderDataModel

interface RemoteFoldersDataSource {
    suspend fun getAllRemoteFolders(): List<FolderDataModel>
    suspend fun getRemoteFolderById(id: String): FolderDataModel
    suspend fun createFolder(folderName: String): String
}