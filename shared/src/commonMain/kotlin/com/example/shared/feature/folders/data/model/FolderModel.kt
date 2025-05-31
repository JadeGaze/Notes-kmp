package com.example.shared.feature.folders.data.model

import com.example.shared.core.common.SectionItem

data class FolderModel(
    val id: String,
    val name: String,
    val notes: Int = 0,
) : SectionItem {
    companion object {
        fun getDefault() = FolderModel(
            id = "0",
            name = "Test text",
            notes = -999
        )
    }
}