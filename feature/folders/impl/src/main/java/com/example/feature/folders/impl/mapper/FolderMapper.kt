package com.example.feature.folders.impl.mapper

import com.example.designsystem.R.drawable
import com.example.designsystem.SectionData
import com.example.feature.folders.impl.presentation.model.FolderUiModel
import com.example.shared.feature.folders.data.model.FolderModel
import com.example.shared.feature.folders.data.model.FoldersListModel

fun List<FoldersListModel>.toUi(): List<SectionData> = map { it.toUi() }

private fun FoldersListModel.toUi(): SectionData {
    return SectionData(
        header = title, items = folders.map { it.toUi() }.toMutableList()
    )
}

fun FolderModel.toUi() =
    FolderUiModel(
        id = id,
        iconId = drawable.outline_folder_24,
        name = name,
        notesNumber = notes
    )