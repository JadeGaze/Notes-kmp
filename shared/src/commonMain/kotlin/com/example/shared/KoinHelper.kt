package com.example.shared

import com.example.shared.feature.auth.ui.SignInViewModel
import com.example.shared.feature.auth.ui.SignUpViewModel
import com.example.shared.feature.folders.ui.FoldersViewModel
import com.example.shared.feature.note.ui.NoteViewModel
import com.example.shared.feature.notes.ui.NotesViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinHelper : KoinComponent {
    private val getSignInViewModel : SignInViewModel by inject()
    fun getSignIn() : SignInViewModel = getSignInViewModel

    private val getSignUpViewModel : SignUpViewModel by inject()
    fun getSignUp() : SignUpViewModel = getSignUpViewModel

    private val getFoldersViewModel : FoldersViewModel by inject()
    fun getFolders() : FoldersViewModel = getFoldersViewModel

    private val getNoteViewModel : NoteViewModel by inject()
    fun getNote() : NoteViewModel = getNoteViewModel

    private val getNotesViewModel : NotesViewModel by inject()
    fun getNotes() : NotesViewModel = getNotesViewModel
}