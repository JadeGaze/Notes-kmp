package com.example.feature.folders.impl.presentation.model

import com.example.shared.core.common.SectionItem

class FolderUiModel(
    val id: String,
    val name: String,
    val notesNumber: Int = 0,
) : SectionItem