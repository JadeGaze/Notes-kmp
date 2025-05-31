package com.example.shared.feature.folders.data.datasources.remote

import com.example.shared.core.database.model.FolderDataModel
import com.example.shared.feature.folders.data.mapper.toFolderData
import com.example.shared.feature.folders.data.mapper.toFoldersData
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class FirebaseFoldersDataSource(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : RemoteFoldersDataSource {
    override suspend fun getAllRemoteFolders(): List<FolderDataModel> {
        return fireStore.collection(FOLDERS_COLLECTION_PATH)
            .where { "userId" equalTo auth.currentUser!!.uid }
            .get().documents
            .toFoldersData()
    }

    override suspend fun getRemoteFolderById(id: String): FolderDataModel {
        return fireStore.collection(FOLDERS_COLLECTION_PATH).document(id).get().toFolderData()
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createFolder(folderName: String): String {
        val id = Uuid.random().toString()
        val folder = hashMapOf(
            "name" to folderName,
            "id" to id,
            "noteCount" to 0,
            "userId" to auth.currentUser!!.uid
        )
        fireStore.collection(FOLDERS_COLLECTION_PATH).document(id).set(folder)
        return id
    }

    companion object {
        private const val FOLDERS_COLLECTION_PATH = "folders"
    }

}
