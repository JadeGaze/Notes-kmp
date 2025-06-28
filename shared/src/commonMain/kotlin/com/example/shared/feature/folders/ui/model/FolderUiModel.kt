package com.example.shared.feature.folders.ui.model

import com.example.shared.core.common.SectionItem

class FolderUiModel(
    val id: String,
    val iconId: Int? = null,
    val name: String,
    val notesNumber: Int = 0,
) : SectionItem {
    companion object {
        fun getDefault() = FolderUiModel(
            id = "0",
            name = "Test text",
            notesNumber = -999
        )
    }
}