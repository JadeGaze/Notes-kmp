package com.example.shared.feature.folders.data.datasources.local

import com.example.shared.core.database.dao.FoldersDao
import com.example.shared.core.database.entity.FolderEntity
import com.example.shared.core.database.model.FolderDataModel

class RoomFoldersDataSource(private val foldersDao: FoldersDao) : LocalFoldersDataSource {
    override suspend fun getAllLocalFolders(): List<FolderDataModel> {
        return foldersDao.getAllLocalFolders()
    }

    override suspend fun getLocalFolderById(id: String): FolderDataModel {
        return foldersDao.getLocalFolderById(id)
    }

    override suspend fun createFolder(folderName: String): Long {
        return foldersDao.createLocalFolder(FolderEntity(name = folderName))
    }

}