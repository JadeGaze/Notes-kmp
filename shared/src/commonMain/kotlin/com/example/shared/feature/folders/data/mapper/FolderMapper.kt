package com.example.shared.feature.folders.data.mapper

import com.example.shared.core.database.entity.FolderEntity
import com.example.shared.core.database.model.FolderDataModel
import com.example.shared.feature.folders.data.model.FolderModel
import dev.gitlive.firebase.firestore.DocumentSnapshot

fun FolderDataModel.toDomain(): FolderModel =
    FolderModel(
        id = id,
        name = name,
        notes = noteCount
    )

fun List<FolderDataModel>.toDomain(): List<FolderModel> = map { it.toDomain() }

fun FolderDataModel.toEntity(): FolderEntity =
    FolderEntity(
        id = id.toLong(),
        name = name,
    )


fun DocumentSnapshot.toFolderData(): FolderDataModel =
    FolderDataModel(
        id = id,
        name = get<String>("name"),
        noteCount = get<Int>("noteCount")
    )

fun List<DocumentSnapshot>.toFoldersData(): List<FolderDataModel> = map { it.toFolderData() }

fun List<FolderDataModel>.toEntity(): List<FolderEntity> = map { it.toEntity() }

fun FolderModel.toFolderData(): FolderDataModel =
    FolderDataModel(
        id = id,
        name = name,
        noteCount = notes
    )
