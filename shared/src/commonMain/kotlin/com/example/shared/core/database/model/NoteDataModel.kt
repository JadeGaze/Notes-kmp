package com.example.shared.core.database.model

import kotlinx.serialization.Serializable

@Serializable
data class NoteDataModel(
    val id: String = "",
    val title: String = "",
    val text: String = "",
    val createDate: Long = 0,
    val editDate: Long = 0,
    val folderId: String = "",
)

data class NoteWithFolderDataModel(
    val note: NoteDataModel = NoteDataModel(
        id = "iriure",
        title = "tincidunt",
        text = "utinam",
        createDate = 9509,
        editDate = 8522,
        folderId = "consectetur"
    ),
    val folder: FolderDataModel = FolderDataModel(
        id = "volutpat",
        name = "Bette Sharpe",
        noteCount = 4577
    ),
)