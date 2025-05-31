package com.example.shared.feature.notes.domain.mapper

import com.example.shared.core.database.entity.NoteEntity
import com.example.shared.core.database.entity.NoteWithFolderEntity
import com.example.shared.core.database.model.NoteDataModel
import com.example.shared.core.database.model.NoteWithFolderDataModel
import com.example.shared.feature.folders.data.model.FolderModel
import com.example.shared.feature.notes.domain.model.NoteDomainModel
import dev.gitlive.firebase.firestore.DocumentSnapshot

fun NoteWithFolderEntity.toDomain(): NoteDomainModel {
    return NoteDomainModel(
        id = note.id.toString(),
        title = note.title,
        text = note.text,
        createDate = note.createDate,
        editDate = note.editDate,
        folder = FolderModel(
            id = folder.id.toString(),
            name = folder.name
        )
    )
}

fun NoteWithFolderDataModel.toDomain(): NoteDomainModel {
    return NoteDomainModel(
        id = note.id,
        title = note.title,
        text = note.text,
        createDate = note.createDate,
        editDate = note.editDate,
        folder = FolderModel(
            id = folder.id,
            name = folder.name
        )
    )
}

fun List<NoteWithFolderEntity>.toDomainFromEntity(): List<NoteDomainModel> = map { it.toDomain() }
fun List<NoteWithFolderDataModel>.toDomain(): List<NoteDomainModel> = map { it.toDomain() }

fun NoteDomainModel.toEntity(): NoteEntity =
    NoteEntity(
        id = id.toLong(),
        title = title,
        text = text,
        createDate = createDate,
        editDate = editDate,
        folderId = folder.id.toLong()
    )

fun NoteDomainModel.toData(): NoteDataModel =
    NoteDataModel(
        id = id,
        title = title,
        text = text,
        createDate = createDate,
        editDate = editDate,
        folderId = folder.id
    )

fun DocumentSnapshot.toNoteData(): NoteDataModel {
//    println("УУУУУУУУУУУУУУУУУУУУ ${get<String>("id")}, ${get<String>("title")}, ${get<String>("text")}, ${get<Long>("createDate")}, ${get<Long>("editDate")}, ${get<String>("fodlerId")}")
    return NoteDataModel(
        id = get<String>("id"),
        title = get<String>("title"),
        text = get<String>("text"),
        createDate = get<Long>("createDate"),
        editDate = get<Long>("editDate"),
        folderId = get<String>("folderId"),
    )
}

fun List<DocumentSnapshot>.toNotesData(): List<NoteDataModel> = map { it.toNoteData() }

