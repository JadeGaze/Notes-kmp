package com.example.shared.feature.folders.data.repository

import com.example.shared.feature.folders.data.datasources.local.LocalFoldersDataSource
import com.example.shared.feature.folders.data.datasources.remote.RemoteFoldersDataSource
import com.example.shared.feature.folders.data.mapper.toDomain
import com.example.shared.feature.folders.data.model.FolderModel
import com.example.shared.feature.folders.data.model.FoldersListModel
import com.example.shared.feature.folders.domain.repository.FoldersRepository

class FoldersRepositoryImpl(
    private val localDataSource: LocalFoldersDataSource,
    private val remoteDataSource: RemoteFoldersDataSource,
) : FoldersRepository {
    override suspend fun getAllFolders(): List<FoldersListModel> {
        return listOf(
            FoldersListModel(
                title = "Local folders",
                folders = getAllLocalFolders()
            ),
            FoldersListModel(
                title = "Remote folders",
                folders = getAllRemoteFolders()
            )
        )
    }

    override suspend fun getAllLocalFolders(): List<FolderModel> {
        return localDataSource.getAllLocalFolders().toDomain()
    }

    override suspend fun getAllRemoteFolders(): List<FolderModel> {
        return remoteDataSource.getAllRemoteFolders().toDomain()
    }

    override suspend fun createFolder(folderName: String, isSync: Boolean): String {
        return if (isSync) {
            remoteDataSource.createFolder(folderName)
        } else {
            localDataSource.createFolder(folderName).toString()
        }
    }
}