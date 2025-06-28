package com.example.notes

import android.app.Application
import com.example.auth.impl.presentation.authPresentationModule
import com.example.feature.folders.impl.foldersPresentationModel
import com.example.impl.notePresentationModule
import com.example.notes.impl.notesPresentationModule
import com.example.shared.CommonKmp
import com.example.shared.dbModule
import com.example.shared.feature.auth.authModule
import com.example.shared.feature.folders.foldersModule
import com.example.shared.feature.note.noteModule
import com.example.shared.feature.notes.notesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NoteApplication)
            modules(
                dbModule,

                authModule,
                authPresentationModule,

                foldersModule,
                foldersPresentationModel,

                notesModule,
                notesPresentationModule,

                noteModule,
                notePresentationModule
            )
        }
    }
}