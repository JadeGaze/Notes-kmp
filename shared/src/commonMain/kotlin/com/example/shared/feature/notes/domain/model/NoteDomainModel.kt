package com.example.shared.feature.notes.domain.model

import com.example.shared.feature.folders.data.model.FolderModel

data class NoteDomainModel(
    val id: String,
    val title: String,
    val text: String,
    val createDate: Long,
    val editDate: Long,
    val folder: FolderModel,
)