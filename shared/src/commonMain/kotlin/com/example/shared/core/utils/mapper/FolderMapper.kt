package com.example.shared.core.utils.mapper

import com.example.shared.core.common.SectionData
import com.example.shared.feature.folders.data.model.FolderModel
import com.example.shared.feature.folders.data.model.FoldersListModel
import com.example.shared.feature.folders.ui.model.FolderUiModel

fun List<FoldersListModel>.toUi(): List<SectionData> = map { it.toUi() }

private fun FoldersListModel.toUi(): SectionData {
    return SectionData(
        header = title, items = folders.map { it.toUi() }.toMutableList()
    )
}

fun FolderModel.toUi() =
    FolderUiModel(
        id = id,
        name = name,
        notesNumber = notes
    )