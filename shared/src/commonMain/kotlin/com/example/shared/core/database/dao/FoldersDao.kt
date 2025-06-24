package com.example.shared.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.shared.core.database.entity.FolderEntity
import com.example.shared.core.database.model.FolderDataModel

@Dao
interface FoldersDao {

    @Transaction
    @Query("SELECT f.*, COUNT(n.id) AS noteCount FROM folders AS f LEFT JOIN notes AS n ON f.id = n.folder_id GROUP BY f.id")
    suspend fun getAllLocalFolders(): List<FolderDataModel>

    @Insert
    suspend fun createLocalFolder(folder: FolderEntity): Long

    @Transaction
    @Query("SELECT *, (SELECT COUNT(*) FROM notes WHERE notes.folder_id = folders.id) AS noteCount FROM folders WHERE id = :id")
    suspend fun getLocalFolderById(id: String): FolderDataModel

}