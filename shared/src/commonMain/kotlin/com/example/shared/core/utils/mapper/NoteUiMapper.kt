package com.example.shared.core.utils.mapper

import com.example.shared.core.utils.resource.TimeUtil
import com.example.shared.feature.folders.data.model.FolderModel
import com.example.shared.feature.note.ui.models.NoteItemUiModel
import com.example.shared.feature.notes.domain.model.NoteDomainModel


fun NoteDomainModel.toUi(): NoteItemUiModel =
    NoteItemUiModel(
        id = id,
        title = title,
        text = text,
        createDate = TimeUtil.formatTimeWithDefaultPattern(createDate),
        editDate = TimeUtil.formatTimeWithDefaultPattern(editDate),
        folder = FolderModel(
            id = folder.id,
            name = folder.name,
        )
    )

fun List<NoteDomainModel>.toUi(): List<NoteItemUiModel> = map { it.toUi() }

fun NoteItemUiModel.toDomain(): NoteDomainModel {
    return NoteDomainModel(
        id = id,
        title = title,
        text = text,
        createDate = TimeUtil.parseTimeToUTCSystemDefaults(createDate),
        editDate = TimeUtil.parseTimeToUTCSystemDefaults(editDate),
        folder = FolderModel(id = folder.id, name = folder.name)
    )
}