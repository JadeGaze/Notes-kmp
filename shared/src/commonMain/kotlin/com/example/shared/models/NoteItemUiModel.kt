package com.example.shared.models

import com.example.shared.feature.folders.data.model.FolderModel

data class NoteItemUiModel(
    val id: String,
    val title: String,
    val text: String,
    val createDate: String,
    val editDate: String,
    val folder: FolderModel,
) {
    companion object {
        fun getDefault() =
            NoteItemUiModel(
                id = "0",
                title = "",
                text = "",
                createDate = "",
                editDate = "",
                folder = FolderModel("-1", "Test")
            )

        fun getDefaultList() =
            listOf(
                getDefault().copy(id = "0"),
                getDefault().copy(id = "19"),
                getDefault().copy(id = "12"),
                getDefault().copy(id = "13"),
                getDefault().copy(id = "14"),
                getDefault().copy(id = "15"),
                getDefault().copy(id = "17"),
                getDefault().copy(id = "16"),
                getDefault().copy(id = "18"),
                getDefault().copy(id = "9"),
                getDefault().copy(id = "10"),
            )
    }
}