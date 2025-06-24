package com.example.shared.feature.notes.data.datasource

import com.example.shared.core.database.model.NoteDataModel
import com.example.shared.core.database.model.NoteWithFolderDataModel
import com.example.shared.feature.folders.data.mapper.toFolderData
import com.example.shared.feature.notes.domain.datasource.RemoteNotesDataSource
import com.example.shared.feature.notes.domain.mapper.toNoteData
import com.example.shared.feature.notes.domain.mapper.toNotesData
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class FirebaseNotesDataSource(
    private val fireStore: FirebaseFirestore,
) : RemoteNotesDataSource {
    override suspend fun getNotesByFolderId(folderId: String): List<NoteWithFolderDataModel> {
        println("WE ARE IN THE REMOTE HEY")
        val folder =
            fireStore.collection(FOLDERS_COLLECTION_PATH).document(folderId).get().toFolderData()
        println("WE GOT THE FOLDER: $folder")

        val notesDoc =
            fireStore.collection(NOTES_COLLECTION_PATH).where { "folderId" equalTo folderId }
                .get().documents[0].get<String>("text")

        println("WE GOT THE NOTES DOC: $notesDoc")
        val notes =
            fireStore.collection(NOTES_COLLECTION_PATH).where { "folderId" equalTo folderId }
                .get().documents.toNotesData()
        println("WE GOT THE NOTES: $notesDoc")
        return notes.map { NoteWithFolderDataModel(it, folder) }
    }

    override suspend fun getNoteById(noteId: String): NoteWithFolderDataModel {
        val note = fireStore.collection(NOTES_COLLECTION_PATH).document(noteId).get()
            .toNoteData()

        val folder = fireStore.collection(FOLDERS_COLLECTION_PATH).document(note.folderId).get()
            .toFolderData()
        return NoteWithFolderDataModel(
            note = note, folder = folder
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createNote(note: NoteDataModel): String {
        val id = Uuid.random().toString()
        fireStore.collection(NOTES_COLLECTION_PATH).document(id).set(note.copy(id = id))
        return id
    }

    override suspend fun updateNote(note: NoteDataModel): String {
        val noteMap = mapOf(
            "id" to note.id,
            "title" to note.title,
            "text" to note.text,
            "createDate" to note.createDate,
            "editDate" to note.editDate,
            "folderId" to note.folderId
        )
        fireStore.collection(NOTES_COLLECTION_PATH).document(note.id).set(noteMap)
        return note.id
    }

    companion object {
        private const val FOLDERS_COLLECTION_PATH = "folders"
        private const val NOTES_COLLECTION_PATH = "notes"
    }
}